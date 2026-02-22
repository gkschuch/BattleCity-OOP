package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import characters.TankPlayer;
import utils.SpriteManager;
import characters.enemy.strategy.*;

public class ArmedTank extends EnemyTank {

	public ArmedTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_ARMED, GameConfig.SPEED_ARMED, GameConfig.SCORE_ARMED, player);

		this.setBehaviorStrategy(new PersuitBehaviorStrategy());
	}

	@Override
	protected void loadSprites() {
		spriteCache.put(Direction.UP, SpriteManager.getSprite(1382, 1203, 149, 190));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(1645, 1241, 204, 138));
		spriteCache.put(Direction.DOWN, SpriteManager.getSprite(1923, 1234, 121, 181));
		spriteCache.put(Direction.LEFT, SpriteManager.getSprite(2104, 1244, 201, 131));
	}

	public EnemyTankType getEnemyTankType() {
		return EnemyTankType.ARMED;
	}
}