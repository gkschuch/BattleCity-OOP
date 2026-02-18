package characters.enemy;

import characters.TankPlayer;
import utils.Direction;
import utils.GameConfig;
import utils.SpriteManager;

public class ArmoredTank extends EnemyTank {

	public ArmoredTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_ARMORED, GameConfig.SPEED_ARMORED, GameConfig.SCORE_ARMORED, player);
	}

	@Override
	protected void loadSprites() {
		spriteCache.put(Direction.UP, SpriteManager.getSprite(284, 1403, 149, 200));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(512, 1231, 185, 138));
		spriteCache.put(Direction.DOWN, SpriteManager.getSprite(757, 1226, 145, 179));
		spriteCache.put(Direction.LEFT, SpriteManager.getSprite(977, 1245, 196, 133));
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
		return EnemyTankType.ARMORED;
	}
}