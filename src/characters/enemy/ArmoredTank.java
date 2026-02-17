package characters.enemy;

import characters.TankPlayer;
import utils.Direction;
import utils.GameConfig;
import java.awt.image.BufferedImage;

public class ArmoredTank extends EnemyTank {

	// contrutor

	public ArmoredTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_ARMORED, GameConfig.SPEED_ARMORED, GameConfig.SCORE_ARMORED, player);
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
				x = 284;
				y = 1403;
				w = 149;
				h = 200;
				break;
			case RIGHT:
				x = 512;
				y = 1231;
				w = 185;
				h = 138;
				break;
			case DOWN:
				x = 757;
				y = 1226;
				w = 145;
				h = 179;
				break;
			case LEFT:
				x = 977;
				y = 1245;
				w = 196;
				h = 133;
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
		return EnemyTankType.ARMORED;
	}
}
