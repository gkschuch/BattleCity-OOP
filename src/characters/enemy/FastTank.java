package characters.enemy;

import utils.Direction;
import utils.GameConfig;

import java.awt.image.BufferedImage;

import characters.TankPlayer;

public class FastTank extends EnemyTank {

	// contrutor

	public FastTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_FAST, GameConfig.SPEED_FAST, GameConfig.SCORE_FAST, player);
	}

	// métodos

	@Override
	public void updateIA() {
		if (this.isFrozen())
			return;

		if (Math.random() > 0.5)
			setDirection(Direction.getRandom());
	}

	@Override
	public BufferedImage getImage() {
		int x = 0, y = 0, w = 0, h = 0;

		switch (this.getDirection()) {
			case UP:
				x = 1395;
				y = 1011;
				w = 133;
				h = 178;
				break;
			case RIGHT:
				x = 1656;
				y = 1054;
				w = 180;
				h = 113;
				break;
			case DOWN:
				x = 1920;
				y = 1046;
				w = 131;
				h = 159;
				break;
			case LEFT:
				x = 2109;
				y = 1047;
				w = 182;
				h = 123;
				break;
		}
		return utils.SpriteManager.getSprite(x, y, w, h);
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
