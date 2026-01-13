package characters.powerups;

import characters.TankPlayer;
import utils.Collectable;

public abstract class PowerUp implements Collectable {
    private int x;
    private int y;
    private boolean active;
    private PowerUpType powerUpType;

    public PowerUp(int x, int y, PowerUpType powerUpType) {
        this.x = x;
        this.y = y;
        this.active = true;
        this.powerUpType = powerUpType;
    }

    @Override
    public abstract void applyEffect(TankPlayer player);

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

}
