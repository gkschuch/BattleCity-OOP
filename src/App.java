import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import characters.TankPlayer;
import characters.enemy.ArmedTank;
import characters.enemy.ArmoredTank;
import characters.enemy.EnemyTank;
import characters.enemy.FastTank;
import characters.enemy.NormalTank;
import grid.Grid;
import grid.blocks.Block;
import projectiles.BasicProjectile;

public class App {

    static class InputState {
        public volatile char lastCmd = 0;
        public volatile boolean running = true;
    }

    static class Shot {
        public final BasicProjectile p;
        public final boolean fromEnemy;

        public Shot(BasicProjectile p, boolean fromEnemy) {
            this.p = p;
            this.fromEnemy = fromEnemy;
        }
    }

    public static void main(String[] args) {
        Grid grid = new Grid();

        TankPlayer player = new TankPlayer("Player1", 1, 1, 20, 1.0);
        player.setX(1);
        player.setY(1);
        player.setDirection(utils.Direction.UP);

        ui.Hud hud = new ui.Hud();

        List<EnemyTank> enemies = new ArrayList<EnemyTank>();
        enemies.add(new NormalTank(10, 2));
        enemies.add(new FastTank(2, 2));
        enemies.add(new ArmedTank(10, 14));
        enemies.add(new ArmoredTank(2, 14));

        // tiros (player + inimigos)
        List<Shot> shots = Collections.synchronizedList(new ArrayList<Shot>());

        InputState input = new InputState();
        startInputThread(input);

        long tick = 0;

        while (input.running) {
            tick++;

            // INPUT
            char cmd = input.lastCmd;
            input.lastCmd = 0;

            if (cmd == 'q')
                break;
            switch (cmd) {
                case 'w':
                    tryMovePlayer(grid, player, utils.Direction.UP, enemies);
                    break;
                case 's':
                    tryMovePlayer(grid, player, utils.Direction.DOWN, enemies);
                    break;
                case 'a':
                    tryMovePlayer(grid, player, utils.Direction.LEFT, enemies);
                    break;
                case 'd':
                    tryMovePlayer(grid, player, utils.Direction.RIGHT, enemies);
                    break;
                case 'f':
                    BasicProjectile p = createShotFromTank(player.getX(), player.getY(), player.getDirection());
                    p.setGrid(grid);
                    p.start();
                    shots.add(new Shot(p, false));
                    break;
                default:
                    break;
            }

            // UPDATE INIMIGOS (andar aleatório a cada ~4 ticks)
            if (tick % 4 == 0) {
                for (int i = 0; i < enemies.size(); i++) {
                    EnemyTank e = enemies.get(i);
                    if (e.isDestroyed())
                        continue;

                    // escolhe direção aleatória às vezes (usa o updateIA do inimigo)
                    e.updateIA();

                    stepEnemy(grid, e, player, enemies);
                }
            }

            // TIRO ALEATÓRIO DOS INIMIGOS (chance por tick)
            for (int i = 0; i < enemies.size(); i++) {
                EnemyTank e = enemies.get(i);
                if (e.isDestroyed())
                    continue;

                if (Math.random() < 0.04) { // 4% por tick
                    BasicProjectile p = createShotFromTank(e.getX(), e.getY(), e.getDirection());
                    p.setGrid(grid);
                    p.start();
                    shots.add(new Shot(p, true));
                }
            }

            // COLISÃO tiro vs tanques
            handleShotsVsTanks(shots, enemies, player);

            // limpa tiros mortos
            cleanupShots(shots);

            // RENDER
            hud.clear();
            hud.draw(player, grid);
            System.out.println(
                    "Legenda: P=player E=inimigo *=tiro X=base x=base destruida T=arvore ~=agua #=parede .=vazio");
            drawWorld(grid, player, enemies, shots);

            // FIM
            if (grid.isBaseDestroyed()) {
                System.out.println("\nGAME OVER: a base foi destruída.");
                break;
            }
            if (player.getLives() <= 0) {
                System.out.println("\nGAME OVER: você ficou sem vidas.");
                break;
            }
            if (countAlive(enemies) == 0) {
                System.out.println("\nVOCE VENCEU: todos os inimigos foram destruídos.");
                break;
            }

            try {
                Thread.sleep(80);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        input.running = false;
    }

    static void startInputThread(InputState input) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (input.running) {
                    String line = sc.nextLine();
                    if (line == null)
                        continue;
                    line = line.trim();
                    if (line.length() == 0)
                        continue;
                    input.lastCmd = Character.toLowerCase(line.charAt(0));
                    if (input.lastCmd == 'q')
                        input.running = false;
                }
                sc.close();
            }
        });
        t.setDaemon(true);
        t.start();
    }

    static void tryMovePlayer(Grid grid, TankPlayer p, utils.Direction dir, List<EnemyTank> enemies) {
        p.setDirection(dir);

        int row = (int) p.getY();
        int col = (int) p.getX();

        int newRow = row + dir.getDy();
        int newCol = col + dir.getDx();

        if (!grid.isInside(newRow, newCol))
            return;
        if (!grid.isWalkable(newRow, newCol))
            return;

        if (isEnemyAt(enemies, newRow, newCol))
            return;

        p.setY(newRow);
        p.setX(newCol);
    }

    static void stepEnemy(Grid grid, EnemyTank e, TankPlayer player, List<EnemyTank> enemies) {
        int row = (int) e.getY();
        int col = (int) e.getX();

        utils.Direction dir = e.getDirection();
        int newRow = row + dir.getDy();
        int newCol = col + dir.getDx();

        if (!grid.isInside(newRow, newCol) || !grid.isWalkable(newRow, newCol)) {
            e.setDirection(utils.Direction.getRandom());
            return;
        }

        if (isEnemyAt(enemies, newRow, newCol))
            return;

        // encostou no player: dá dano simples e não entra na célula
        if (isPlayerAt(player, newRow, newCol)) {
            player.takeDamage(1);
            return;
        }

        e.setY(newRow);
        e.setX(newCol);
    }

    static BasicProjectile createShotFromTank(double tankX, double tankY, utils.Direction tankDir) {
        projectiles.Direction pd = toProjectileDirection(tankDir);

        int startX = (int) tankX + pd.getDx();
        int startY = (int) tankY + pd.getDy();

        return new BasicProjectile(startX, startY, pd);
    }

    static projectiles.Direction toProjectileDirection(utils.Direction d) {
        if (null == d) {
            return projectiles.Direction.RIGHT;
        } else {
            switch (d) {
                case UP:
                    return projectiles.Direction.UP;
                case DOWN:
                    return projectiles.Direction.DOWN;
                case LEFT:
                    return projectiles.Direction.LEFT;
                default:
                    return projectiles.Direction.RIGHT;
            }
        }
    }

    static void handleShotsVsTanks(List<Shot> shots, List<EnemyTank> enemies, TankPlayer player) {
        synchronized (shots) {
            for (int i = 0; i < shots.size(); i++) {
                Shot s = shots.get(i);
                BasicProjectile p = s.p;
                if (!p.isActive())
                    continue;

                int r = p.getY();
                int c = p.getX();

                if (s.fromEnemy) {
                    if (isPlayerAt(player, r, c)) {
                        player.takeDamage(p.getDamage());
                        p.deactivate();
                    }
                } else {
                    for (int j = 0; j < enemies.size(); j++) {
                        EnemyTank e = enemies.get(j);
                        if (e.isDestroyed())
                            continue;
                        if ((int) e.getY() == r && (int) e.getX() == c) {
                            e.takeDamage(p.getDamage());
                            p.deactivate();
                            break;
                        }
                    }
                }
            }
        }
    }

    static void cleanupShots(List<Shot> shots) {
        synchronized (shots) {
            Iterator<Shot> it = shots.iterator();
            while (it.hasNext()) {
                Shot s = it.next();
                if (!s.p.isActive())
                    it.remove();
            }
        }
    }

    static void drawWorld(Grid grid, TankPlayer player, List<EnemyTank> enemies, List<Shot> shots) {
        int pr = (int) player.getY();
        int pc = (int) player.getX();

        for (int r = 0; r < grid.getRows(); r++) {
            StringBuilder sb = new StringBuilder();

            for (int c = 0; c < grid.getCols(); c++) {
                char ch;

                if (r == pr && c == pc)
                    ch = 'P';
                else if (isEnemyAt(enemies, r, c))
                    ch = 'E';
                else if (isShotAt(shots, r, c))
                    ch = '*';
                else
                    ch = charFor(grid.getBlock(r, c));

                sb.append(ch);
            }

            System.out.println(sb.toString());
        }
    }

    static boolean isShotAt(List<Shot> shots, int row, int col) {
        synchronized (shots) {
            for (int i = 0; i < shots.size(); i++) {
                BasicProjectile p = shots.get(i).p;
                if (p.isActive() && p.getY() == row && p.getX() == col)
                    return true;
            }
        }
        return false;
    }

    static boolean isPlayerAt(TankPlayer p, int row, int col) {
        return ((int) p.getY() == row && (int) p.getX() == col);
    }

    static boolean isEnemyAt(List<EnemyTank> enemies, int row, int col) {
        for (int i = 0; i < enemies.size(); i++) {
            EnemyTank e = enemies.get(i);
            if (e.isDestroyed())
                continue;
            if ((int) e.getY() == row && (int) e.getX() == col)
                return true;
        }
        return false;
    }

    static int countAlive(List<EnemyTank> enemies) {
        int n = 0;
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isDestroyed())
                n++;
        }
        return n;
    }

    static char charFor(Block b) {
        if (b == null)
            return '.';

        if (b.isBase()) {
            if (b.isDestroyed())
                return 'x';
            else
                return 'X';
        }

        if (b.isWalkable()) {
            if (b.isProjectilePassThrough())
                return 'T';
            else
                return '.';
        } else {
            if (b.isProjectilePassThrough())
                return '~';
            else
                return '#';
        }
    }
}
