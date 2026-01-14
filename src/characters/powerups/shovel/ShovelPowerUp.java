package characters.powerups.shovel;

import characters.TankPlayer;
import characters.powerups.PowerUp;
import characters.powerups.PowerUpType;
import grid.Grid;

public class ShovelPowerUp extends PowerUp {
    private final Grid grid;

    public ShovelPowerUp(int x, int y, Grid grid) {
        super(x, y, PowerUpType.SHOVEL);
        this.grid = grid;
    }

    @Override
    public void applyEffect(TankPlayer player) {
        Thread shovelThread = new Thread(new ShovelTask(grid));
        shovelThread.start();
    }

}
