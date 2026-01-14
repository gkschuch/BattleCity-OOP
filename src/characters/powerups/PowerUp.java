package characters.powerups;

import characters.TankPlayer;
import grid.Grid;
import grid.blocks.Block;
import utils.Collectable;

public abstract class PowerUp extends Block implements Collectable {
    private PowerUpType powerUpType;

    public PowerUp(int x, int y, PowerUpType powerUpType) {
        super(x, y);
        this.powerUpType = powerUpType;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void onPlayerStep(TankPlayer player, Grid grid) {
        this.applyEffect(player);
        grid.setBlock(this.getRow(), this.getCol(), null);
    }

    @Override
    public char getIcon() {
        return '?';
    }

    @Override
    public abstract void applyEffect(TankPlayer player);

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

}
