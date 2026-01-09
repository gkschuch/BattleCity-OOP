import characters.TankPlayer;
import grid.Grid;
import grid.blocks.Block;
import java.util.Scanner;
import ui.Hud;

public class App {

    public static void main(String[] args) {
        Grid grid = new Grid();

        TankPlayer player = new TankPlayer("Player1", 1, 1, 3, 1.0);
        player.setX(1);
        player.setY(1);
        player.setDirection(utils.Direction.UP);

        Hud hud = new Hud();
        Scanner sc = new Scanner(System.in);

        OUTER: while (true) {
            hud.clear();
            hud.draw(player, grid);
            System.out.println("Legenda: P=player  X=base  x=base destruida  T=arvore  ~=agua  #=parede  .=vazio");
            drawGrid(grid, player);
            if (grid.isBaseDestroyed()) {
                System.out.println("\nGAME OVER: a base foi destruída.");
                break;
            }
            System.out.print("\nComando (W/A/S/D, F, Q): ");
            String line = sc.nextLine();
            if (line == null)
                continue;
            line = line.trim();
            if (line.length() == 0)
                continue;
            char cmd = Character.toLowerCase(line.charAt(0));
            switch (cmd) {
                case 'q':
                    break OUTER;
                case 'w':
                    tryMove(grid, player, utils.Direction.UP);
                    break;
                case 's':
                    tryMove(grid, player, utils.Direction.DOWN);
                    break;
                case 'a':
                    tryMove(grid, player, utils.Direction.LEFT);
                    break;
                case 'd':
                    tryMove(grid, player, utils.Direction.RIGHT);
                    break;
                case 'f':
                    shootRay(grid, player);
                    break;
                default:
                    break;
            }
        }

        sc.close();
    }

    static void tryMove(Grid grid, TankPlayer p, utils.Direction dir) {
        p.setDirection(dir);

        int row = (int) p.getY();
        int col = (int) p.getX();

        int newRow = row + dir.getDy();
        int newCol = col + dir.getDx();

        if (!grid.isInside(newRow, newCol))
            return;

        if (grid.isWalkable(newRow, newCol)) {
            p.setY(newRow);
            p.setX(newCol);
        }
    }

    static void shootRay(Grid grid, TankPlayer p) {
        utils.Direction d = p.getDirection();

        int row = (int) p.getY();
        int col = (int) p.getX();

        int r = row + d.getDy();
        int c = col + d.getDx();

        while (grid.isInside(r, c)) {
            boolean pass = grid.handleProjectileHit(r, c); // true atravessa; false para
            if (!pass)
                break;

            r = r + d.getDy();
            c = c + d.getDx();
        }
    }

    static void drawGrid(Grid grid, TankPlayer p) {
        int pr = (int) p.getY();
        int pc = (int) p.getX();

        for (int r = 0; r < grid.getRows(); r++) {
            StringBuilder sb = new StringBuilder();

            for (int c = 0; c < grid.getCols(); c++) {
                if (r == pr && c == pc) {
                    sb.append('P');
                } else {
                    Block b = grid.getBlock(r, c);
                    sb.append(charFor(b));
                }
            }

            System.out.println(sb.toString());
        }
    }

    static char charFor(Block b) {
        if (b == null) {
            return '.';
        } else {
            if (b.isBase()) {
                if (b.isDestroyed())
                    return 'x';
                else
                    return 'X';
            } else {
                // Árvore: tanque passa e projétil passa
                if (b.isWalkable()) {
                    if (b.isProjectilePassThrough())
                        return 'T';
                    else
                        return '.'; // (não existe bloco assim no teu projeto atual)
                } else {
                    // Água: tanque não passa, projétil passa
                    if (b.isProjectilePassThrough())
                        return '~';
                    // Parede sólida: tanque não passa, projétil não passa (Brick ou Steel)
                    else
                        return '#';
                }
            }
        }
    }
}
