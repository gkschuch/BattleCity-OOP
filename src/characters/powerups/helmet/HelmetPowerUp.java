package characters.powerups.helmet;

import characters.TankPlayer;
import characters.powerups.PowerUp;
import characters.powerups.PowerUpType;

public class HelmetPowerUp extends PowerUp {
    public HelmetPowerUp(int x, int y) {
        super(x, y, PowerUpType.HELMET);
    }

    @Override
    public void applyEffect(TankPlayer player) {
        Thread effectThread = new Thread(new InvulnerabilityTask(player));
        effectThread.start();
    }
}
