package characters.powerups;

import java.util.List;
import java.util.Random;

import characters.enemy.EnemyTank;
import characters.powerups.clock.ClockPowerUp;
import characters.powerups.helmet.HelmetPowerUp;
import characters.powerups.shovel.ShovelPowerUp;
import grid.Grid;

public class PowerUpFactory {
    private static final Random random = new Random();

    public static PowerUp createRandom(int row, int col, Grid grid, List<EnemyTank> enemies) {
        int typeIndex = random.nextInt(PowerUpType.values().length);
        PowerUpType type = PowerUpType.values()[typeIndex];
        return switch (type) {
            case STAR -> new StarPowerUp(row, col);
            case HELMET -> new HelmetPowerUp(row, col);
            case LIFE -> new LifePowerUp(row, col);
            case BOMB -> new BombPowerUp(row, col, enemies);
            case SHOVEL -> new ShovelPowerUp(row, col, grid);
            case CLOCK -> new ClockPowerUp(row, col, enemies);

        };
    }
}
