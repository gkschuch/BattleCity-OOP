package characters.powerups;

import characters.TankPlayer;

public class StarPowerUp extends PowerUp {
    public StarPowerUp(int x, int y) {
        super(x, y, PowerUpType.STAR);
    }

    @Override
    public void applyEffect(TankPlayer player) {
        player.upgradeGunLevel();
        deactivate();
    }

}
