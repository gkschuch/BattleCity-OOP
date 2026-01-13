package characters.powerups;

import characters.TankPlayer;
import utils.Collectable;

public abstract class PowerUp implements Collectable {
    private int x;
    private int y;
    private boolean active;

    public PowerUp(int x, int y) {
        this.x = x;
        this.y = y;
        this.active = true;
    }

    @Override
    public abstract void applyEffect(TankPlayer player);

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

}
