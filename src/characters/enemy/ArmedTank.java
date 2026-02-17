package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import characters.TankPlayer;
import java.awt.image.BufferedImage;

public class ArmedTank extends EnemyTank {

	// contrutor

	public ArmedTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_ARMED, GameConfig.SPEED_ARMED, GameConfig.SCORE_ARMED, player);
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
				x = 1382;
				y = 1203;
				w = 149;
				h = 190;
				break;
			case RIGHT:
				x = 1645;
				y = 1241;
				w = 204;
				h = 138;
				break;
			case DOWN:
				x = 1923;
				y = 1234;
				w = 121;
				h = 181;
				break;
			case LEFT:
				x = 2104;
				y = 1244;
				w = 201;
				h = 131;
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
		return EnemyTankType.ARMED;
	}
}
