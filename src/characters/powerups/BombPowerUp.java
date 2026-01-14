package characters.powerups;

import java.util.List;

import characters.TankPlayer;
import characters.enemy.EnemyTank;

public class BombPowerUp extends PowerUp {
    private List<EnemyTank> enemies;

    public BombPowerUp(int x, int y, List<EnemyTank> enemies) {
        super(x, y, PowerUpType.BOMB);
        this.enemies = enemies;
    }

    @Override
    public void applyEffect(TankPlayer player) {
        for (EnemyTank enemyTank : enemies) {
            enemyTank.takeDamage(99999);
        }
        System.out.println("BOMB: All enemies destroyed");
    }

}
