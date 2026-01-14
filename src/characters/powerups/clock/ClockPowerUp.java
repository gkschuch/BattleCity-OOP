package characters.powerups;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import java.util.List;

public class ClockPowerUp extends PowerUp {
    private List<EnemyTank> enemies;

    public ClockPowerUp(int x, int y, List<EnemyTank> enemies) {
        super(x, y, PowerUpType.CLOCK);
        this.enemies = enemies;
    }

    @Override
    public void applyEffect(TankPlayer player) {
        this.deactivate();

        Thread freezeThread = new Thread(new FreezeEnemiesTask(enemies));
        freezeThread.start();
    }
}
