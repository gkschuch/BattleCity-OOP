package characters.powerups;

import characters.TankPlayer;

public class LifePowerUp extends PowerUp {
    public LifePowerUp(int x, int y) {
        super(x, y, PowerUpType.LIFE);
    }

    @Override
    public void applyEffect(TankPlayer player) {
        player.addLives();
        this.deactivate();
    }

}
