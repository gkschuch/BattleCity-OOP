package game;

import java.util.List;
import java.util.Random;

import characters.enemy.EnemyTank;
import characters.powerups.PowerUp;
import characters.powerups.PowerUpFactory;
import grid.Grid;

public class PowerUpSpawner implements Runnable {
    private final Grid grid;
    private final List<EnemyTank> enemies;
    private final Random random = new Random();
    private boolean running = true;

    public PowerUpSpawner(Grid grid, List<EnemyTank> enemies) {
        this.grid = grid;
        this.enemies = enemies;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(5000 + random.nextInt(5000));
                int r = random.nextInt(grid.getRows());
                int c = random.nextInt(grid.getCols());

                if (grid.getBlock(r, c) == null) {
                    PowerUp powerUp = PowerUpFactory.createRandom(r, c, grid, enemies);
                    grid.setBlock(r, c, powerUp);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }

}
