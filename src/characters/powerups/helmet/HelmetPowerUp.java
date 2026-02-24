package characters.powerups.helmet;

import java.util.List;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import characters.powerups.PowerUp;
import characters.powerups.PowerUpType;

public class HelmetPowerUp extends PowerUp {
    List<EnemyTank> enemies;

    public HelmetPowerUp(int x, int y, List<EnemyTank> enemies) {
        super(x, y, PowerUpType.HELMET);
        this.enemies = enemies;
    }

    @Override
    public void applyEffect(TankPlayer player) {
        Thread effectThread = new Thread(new InvulnerabilityTask(player, enemies));
        effectThread.start();
    }
}
