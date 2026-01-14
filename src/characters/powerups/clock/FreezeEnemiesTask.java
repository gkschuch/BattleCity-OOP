package characters.powerups.clock;

import characters.enemy.EnemyTank;
import utils.GameConfig;
import java.util.List;

public class FreezeEnemiesTask implements Runnable {
    private final List<EnemyTank> enemies;

    public FreezeEnemiesTask(List<EnemyTank> enemies) {
        this.enemies = enemies;
    }

    @Override
    public void run() {
        try {
            for (EnemyTank enemyTank : enemies) {
                enemyTank.setFrozen(true);
            }

            Thread.sleep(GameConfig.CLOCK_DURATION_MS);
        } catch (InterruptedException e) {
            System.err.println("Frozen effect interrupted");
            Thread.currentThread().interrupt();
        } finally {
            for (EnemyTank enemyTank : enemies) {
                enemyTank.setFrozen(false);
            }
        }
    }

}
