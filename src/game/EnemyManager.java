package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import characters.TankPlayer;
import characters.enemy.*;
import grid.Grid;

public class EnemyManager {
    private final List<EnemyTank> enemies = new ArrayList<>();
    private final Random random = new Random();

    private int[] findEmptyPosition(Grid grid, TankPlayer player) {
        int r, c;
        boolean isOccupied;
        do {
            r = random.nextInt(grid.getRows());
            c = random.nextInt(grid.getCols());

            boolean isObstacle = !grid.isWalkable(r, c);
            boolean isAtPlayerPosition = (r == (int) player.getY() && c == (int) player.getX());

            isOccupied = isObstacle || isAtPlayerPosition;

        } while (isOccupied);
        return new int[] { r, c };
    }

    private EnemyTank createTankByIndex(int index, TankPlayer player, Grid grid) {
        int[] pos = findEmptyPosition(grid, player);
        int r = pos[0], c = pos[1];

        return switch (index) {
            case 0 -> new NormalTank(c, r, player);
            case 1 -> new FastTank(c, r, player);
            case 2 -> new ArmedTank(c, r, player);
            default -> new ArmoredTank(c, r, player);
        };
    }

    protected void spawnEnemies(int difficulty, TankPlayer player, Grid grid) {
        enemies.clear();

        int count = switch (difficulty) {
            case 1 -> 2;
            case 2 -> 3;
            default -> 4;
        };

        for (int i = 0; i < count; i++) {
            EnemyTank enemy = createTankByIndex(i, player, grid);
            if (enemy != null) {
                enemies.add(enemy);
                enemy.start();
            }
        }
    }

    protected void updateEnemies(Grid grid, TankPlayer player, long tick) {
        if (tick % 4 == 0)
            for (EnemyTank e : enemies)
                if (e != null && !e.isDestroyed())
                    MovementSystem.stepEnemy(grid, e, player, enemies);
    }

    protected void stopAll() {
        for (EnemyTank enemyTank : enemies)
            if (enemyTank != null)
                enemyTank.stop();
    }

    protected List<EnemyTank> getEnemies() {
        return enemies;
    }

    protected int countAlive() {
        return MovementSystem.countAlive(enemies);
    }
}
