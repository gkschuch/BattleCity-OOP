package characters.powerups;

import characters.TankPlayer;

public class HelmetPowerUp extends PowerUp {
    public HelmetPowerUp(int x, int y) {
        super(x, y, PowerUpType.HELMET);
    }

    @Override
    public void applyEffect(TankPlayer player) {
        this.deactivate();

        Thread effectThread = new Thread(new InvulnerabilityTask(player));
        effectThread.start();
    }
}
