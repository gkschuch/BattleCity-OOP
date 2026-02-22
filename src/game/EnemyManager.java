package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import characters.TankPlayer;
import characters.enemy.*;
import characters.enemy.strategy.*;
import game.persistence.EnemySaveData;
import grid.Grid;

public class EnemyManager {
    private final List<EnemyTank> enemies = new ArrayList<>();
    private final Random random = new Random();

    private int[] findEmptyPosition(Grid grid, TankPlayer player) {
        int r, c;
        boolean isOccupied;
        do {
            r = random.nextInt(2) + 1;
            c = random.nextInt(grid.getCols());

            boolean isObstacle = !grid.isWalkable(r, c);
            boolean isAtPlayerPosition = (r == (int) player.getY() && c == (int) player.getX());

            isOccupied = isObstacle || isAtPlayerPosition;

        } while (isOccupied);

        return new int[] { r, c };
    }

    private EnemyTank createTank(EnemyTankType type, TankPlayer player, Grid grid) {
        int[] pos = findEmptyPosition(grid, player);
        int r = pos[0], c = pos[1];

        return switch (type) {
            case NORMAL -> new NormalTank(c, r, player);
            case FAST -> new FastTank(c, r, player);
            case ARMED -> new ArmedTank(c, r, player);
            case ARMORED -> new ArmoredTank(c, r, player);
        };
    }

    protected void spawnEnemies(int difficulty, TankPlayer player, Grid grid) {
        enemies.clear();

        switch (difficulty) {
            case 1 -> spawnEasy(player, grid);
            case 2 -> spawnMedium(player, grid);
            case 3 -> spawnHard(player, grid);
            default -> spawnEasy(player, grid);
        }
    }

    private void spawnEasy(TankPlayer player, Grid grid) {
        for (int i = 0; i < 4; i++) {
            EnemyTank tank = createTank(EnemyTankType.NORMAL, player, grid);
            tank.setBehaviorStrategy(new RandomBehaviorStrategy());

            enemies.add(tank);
            tank.setGrid(grid);
            tank.start();
        }
    }

    private void spawnMedium(TankPlayer player, Grid grid) {
        EnemyTank fast = createTank(EnemyTankType.FAST, player, grid);
        fast.setBehaviorStrategy(new PersuitBehaviorStrategy());

        EnemyTank armed = createTank(EnemyTankType.ARMED, player, grid);
        armed.setBehaviorStrategy(new PersuitBehaviorStrategy());

        EnemyTank normal = createTank(EnemyTankType.NORMAL, player, grid);
        normal.setBehaviorStrategy(new FleeBehaviorStrategy());

        EnemyTank armored = createTank(EnemyTankType.ARMORED, player, grid);
        armored.setBehaviorStrategy(new RandomBehaviorStrategy());

        addAndStartTanks(grid, fast, armed, normal, armored);
    }

    private void spawnHard(TankPlayer player, Grid grid) {
        EnemyTank armed1 = createTank(EnemyTankType.ARMED, player, grid);
        EnemyTank armed2 = createTank(EnemyTankType.ARMED, player, grid);
        EnemyTank armed3 = createTank(EnemyTankType.ARMED, player, grid);
        EnemyTank armored = createTank(EnemyTankType.ARMORED, player, grid);
        EnemyTank fast = createTank(EnemyTankType.FAST, player, grid);

        PersuitBehaviorStrategy pursuit = new PersuitBehaviorStrategy();
        armed1.setBehaviorStrategy(pursuit);
        armed2.setBehaviorStrategy(pursuit);
        armed3.setBehaviorStrategy(pursuit);
        armored.setBehaviorStrategy(pursuit);
        fast.setBehaviorStrategy(pursuit);

        addAndStartTanks(grid, armed1, armed2, armed3, armored, fast);
    }

    private void addAndStartTanks(Grid grid, EnemyTank... tanksList) {
        for (EnemyTank tank : tanksList) {
            enemies.add(tank);
            tank.setGrid(grid);
            tank.start();
        }
    }

    protected void updateEnemies(Grid grid, TankPlayer player, long tick) {
        if (tick % 4 == 0)
            for (EnemyTank e : enemies)
                if (e != null && !e.isDestroyed())
                    MovementSystem.stepEnemy(grid, e, player, enemies);
    }

    public void addSavedEnemy(EnemySaveData eData, TankPlayer player, Grid grid) {
        EnemyTank enemy = null;
        switch (eData.type) { //
            case NORMAL -> enemy = new NormalTank(eData.x, eData.y, player);
            case FAST -> enemy = new FastTank(eData.x, eData.y, player);
            case ARMORED -> enemy = new ArmoredTank(eData.x, eData.y, player);
            case ARMED -> enemy = new ArmedTank(eData.x, eData.y, player);
        }
        if (enemy == null)
            return;

        enemy.setLives(eData.lives);
        enemy.setFrozen(eData.frozen);
        enemy.setDirection(eData.direction);
        enemy.setGrid(grid);

        this.enemies.add(enemy);
        enemy.start();
    }

    public void clearEnemies() {
        stopAll();
        enemies.clear();
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
