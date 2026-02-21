package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import utils.SpriteManager;
import characters.TankPlayer;
import characters.enemy.strategy.*;

public class FastTank extends EnemyTank {

	public FastTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_FAST, GameConfig.SPEED_FAST, GameConfig.SCORE_FAST, player);

		this.setBehaviorStrategy(new PersuitBehaviorStrategy());
	}

	@Override
	protected void loadSprites() {
		spriteCache.put(Direction.UP, SpriteManager.getSprite(1395, 1011, 133, 178));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(1656, 1054, 180, 113));
		spriteCache.put(Direction.DOWN, SpriteManager.getSprite(1920, 1046, 131, 159));
		spriteCache.put(Direction.LEFT, SpriteManager.getSprite(2109, 1047, 182, 123));
	}

	@Override
	public void shoot() {
		if (this.isFrozen())
			return;
	}

	public EnemyTankType getEnemyTankType() {
		return EnemyTankType.FAST;
	}
}