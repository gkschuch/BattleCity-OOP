package characters.powerups;

import characters.TankPlayer;
import characters.exceptions.PowerUpException;
import grid.Grid;
import grid.blocks.Block;
import utils.Collectable;

public abstract class PowerUp extends Block implements Collectable {
    private PowerUpType powerUpType;

    public PowerUp(int x, int y, PowerUpType powerUpType) {
        if ((x < 0 || x > 13) || (y < 0 || y > 17))
            throw new PowerUpException("Coordenadas de spawn inválidas: " + x + "," + y);
        if (powerUpType == null)
            throw new PowerUpException("Tipo de PowerUp não pode ser nulo.");
        super(x, y);
        this.powerUpType = powerUpType;
    }

    @Override
    public java.awt.Color getColor() {
        return java.awt.Color.MAGENTA;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void onPlayerStep(TankPlayer player, Grid grid) {
        this.applyEffect(player);
        grid.setBlock(this.getRow(), this.getCol(), null);
        grid.removePowerUp(this);
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
