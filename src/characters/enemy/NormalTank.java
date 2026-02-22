package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import utils.SpriteManager;
import characters.TankPlayer;
import characters.enemy.strategy.*;

public class NormalTank extends EnemyTank {

	public NormalTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_NORMAL, GameConfig.SPEED_NORMAL, GameConfig.SCORE_NORMAL, player);

		this.setBehaviorStrategy(new PersuitBehaviorStrategy());
	}

	@Override
	protected void loadSprites() {
		spriteCache.put(Direction.UP, SpriteManager.getSprite(313, 1010, 123, 183));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(488, 850, 204, 145));
		spriteCache.put(Direction.DOWN, SpriteManager.getSprite(771, 862, 131, 164));
		spriteCache.put(Direction.LEFT, SpriteManager.getSprite(985, 861, 191, 128));
	}

	public EnemyTankType getEnemyTankType() {
		return EnemyTankType.NORMAL;
	}
}