package game;

import characters.TankPlayer;
import characters.enemy.ArmedTank;
import characters.enemy.ArmoredTank;
import characters.enemy.EnemyTank;
import characters.enemy.FastTank;
import characters.enemy.NormalTank;
import grid.Grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ui.Hud;

public class Game {
    // atributos

    private final InputHandler input;

    // construtor

    public Game() {
        this.input = new InputHandler();
    }

    // métodos

    public void run() {
        Grid grid = new Grid();

        TankPlayer player = new TankPlayer("Player1", 1, 1, 20, 1.0);
        player.setX(1);
        player.setY(1);
        player.setDirection(utils.Direction.UP);

        Hud hud = new Hud();

        List<EnemyTank> enemies = new ArrayList<>();
        enemies.add(new NormalTank(10, 2));
        enemies.add(new FastTank(2, 2));
        enemies.add(new ArmedTank(10, 14));
        enemies.add(new ArmoredTank(2, 14));

        List<Shot> shots = Collections.synchronizedList(new ArrayList<>());

        input.start();

        long tick = 0;

        while (input.getRunning()) {
            tick++;

            char cmd = input.pollCommand();

            if (cmd == 'q')
                break;

            switch (cmd) {
                case 'w' -> MovementSystem.tryMovePlayer(grid, player, utils.Direction.UP, enemies);

                case 's' -> MovementSystem.tryMovePlayer(grid, player, utils.Direction.DOWN, enemies);

                case 'a' -> MovementSystem.tryMovePlayer(grid, player, utils.Direction.LEFT, enemies);

                case 'd' -> MovementSystem.tryMovePlayer(grid, player, utils.Direction.RIGHT, enemies);

                case 'f' -> ShotSystem.playerShoot(shots, grid, player);

                default -> {
                }
            }

            if (tick % 4 == 0) {
                for (int i = 0; i < enemies.size(); i++) {
                    EnemyTank e = enemies.get(i);

                    if (e == null)
                        continue;

                    if (e.isDestroyed())
                        continue;

                    e.updateIA();
                    MovementSystem.stepEnemy(grid, e, player, enemies);
                }
            }

            ShotSystem.enemiesRandomShoot(shots, grid, enemies, 0.04);

            game.CollisionSystem.handleShotsVsTanks(shots, enemies, player);

            ShotSystem.cleanupShots(shots);

            ConsoleRenderer.render(hud, grid, player, enemies, shots);

            if (grid.isBaseDestroyed()) {
                System.out.println("\nGAME OVER: a base foi destruída.");
                break;
            }

            if (player.getLives() <= 0) {
                System.out.println("\nGAME OVER: você ficou sem vidas.");
                break;
            }

            if (MovementSystem.countAlive(enemies) == 0) {
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

        input.stop();
        ShotSystem.stopAllShots(shots);
    }
}
