package characters.powerups;

import java.util.List;
import java.util.Random;

import characters.enemy.EnemyTank;
import characters.exceptions.PowerUpException;
import characters.exceptions.PowerUpDependencyException;
import characters.powerups.clock.ClockPowerUp;
import characters.powerups.helmet.HelmetPowerUp;
import characters.powerups.shovel.ShovelPowerUp;
import grid.Grid;

public class PowerUpFactory {
    private static final Random random = new Random();

    public static PowerUp createRandom(int row, int col, Grid grid, List<EnemyTank> enemies) {
        PowerUpType type = getRandomTypeByRarity();
        return createByType(type, row, col, grid, enemies);
    }

    private static PowerUpType getRandomTypeByRarity() {
        int chance = random.nextInt(100);

        if (chance < 26)
            return PowerUpType.STAR;

        if (chance < 52)
            return PowerUpType.LIFE;

        if (chance < 67)
            return PowerUpType.HELMET;

        if (chance < 82)
            return PowerUpType.CLOCK;

        if (chance < 97)
            return PowerUpType.SHOVEL;

        return PowerUpType.BOMB;
    }

    public static PowerUp createByType(PowerUpType type, int row, int col, Grid grid, List<EnemyTank> enemies) {
        if (type == null)
            throw new PowerUpException("Tentativa de criar PowerUp com tipo nulo na linha " + row);

        return switch (type) {
            case STAR -> new StarPowerUp(row, col);
            case HELMET -> new HelmetPowerUp(row, col);
            case LIFE -> new LifePowerUp(row, col);
            case BOMB -> {
                if (enemies == null)
                    throw new PowerUpDependencyException("BOMB", "List<EnemyTank>");
                yield new BombPowerUp(row, col, enemies);
            }
            case SHOVEL -> {
                if (grid == null)
                    throw new PowerUpDependencyException("SHOVEL", "Grid");
                yield new ShovelPowerUp(row, col, grid);
            }
            case CLOCK -> {
                if (enemies == null)
                    throw new PowerUpDependencyException("CLOCK", "List<EnemyTank>");
                yield new ClockPowerUp(row, col, enemies);
            }
        };
    }
}