package characters.powerups;

import java.util.List;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import characters.exceptions.PowerUpDependencyException;

public class BombPowerUp extends PowerUp {
    private List<EnemyTank> enemies;

    public BombPowerUp(int x, int y, List<EnemyTank> enemies) {
        super(x, y, PowerUpType.BOMB);
        this.enemies = enemies;
    }

    @Override
    public void applyEffect(TankPlayer player) {
        if (enemies == null)
            throw new PowerUpDependencyException("BOMB", "Enemy List");
        synchronized (enemies) {
            for (EnemyTank enemyTank : enemies) {
                if (enemyTank != null)
                    enemyTank.takeDamage(9999);
            }
        }
        System.out.println("BOMB: All enemies destroyed");
    }

}
