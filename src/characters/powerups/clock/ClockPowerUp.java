package characters.powerups.clock;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import characters.powerups.PowerUp;
import characters.powerups.PowerUpType;
import java.util.List;

public class ClockPowerUp extends PowerUp {
    private List<EnemyTank> enemies;

    public ClockPowerUp(int x, int y, List<EnemyTank> enemies) {
        super(x, y, PowerUpType.CLOCK);
        this.enemies = enemies;
    }

    @Override
    public void applyEffect(TankPlayer player) {
        if (enemies.isEmpty())
            return;
        Thread freezeThread = new Thread(new FreezeEnemiesTask(enemies));
        freezeThread.start();
    }
}
