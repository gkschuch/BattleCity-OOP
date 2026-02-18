package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import utils.SpriteManager;
import characters.TankPlayer;

public class NormalTank extends EnemyTank {

	public NormalTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_NORMAL, GameConfig.SPEED_NORMAL, GameConfig.SCORE_NORMAL, player);
	}

	@Override
	protected void loadSprites() {
		spriteCache.put(Direction.UP, SpriteManager.getSprite(313, 1010, 123, 183));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(488, 850, 204, 145));
		spriteCache.put(Direction.DOWN, SpriteManager.getSprite(771, 862, 131, 164));
		spriteCache.put(Direction.LEFT, SpriteManager.getSprite(985, 861, 191, 128));
	}

	@Override
	public void updateIA() {
		if (this.isFrozen())
			return;
		if (Math.random() > 0.5)
			setDirection(Direction.getRandom());
	}

	@Override
	public void shoot() {
		if (this.isFrozen())
			return;
	}

	public EnemyTankType getEnemyTankType() {
		return EnemyTankType.NORMAL;
	}
}