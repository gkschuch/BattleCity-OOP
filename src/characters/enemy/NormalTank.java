package characters.enemy;

import utils.Direction;
import utils.GameConfig;

import java.awt.image.BufferedImage;

import characters.TankPlayer;

public class NormalTank extends EnemyTank {

	// contrutor

	public NormalTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_NORMAL, GameConfig.SPEED_NORMAL, GameConfig.SCORE_NORMAL, player);
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
				x = 313;
				y = 1010;
				w = 123;
				h = 183;
				break;
			case RIGHT:
				x = 488;
				y = 850;
				w = 204;
				h = 145;
				break;
			case DOWN:
				x = 771;
				y = 862;
				w = 131;
				h = 164;
				break;
			case LEFT:
				x = 985;
				y = 861;
				w = 191;
				h = 128;
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
		return EnemyTankType.NORMAL;
	}
}
