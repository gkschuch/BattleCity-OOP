package characters.enemy;

import utils.Direction;
import utils.GameConfig;
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
	public void shoot() {
		if (this.isFrozen())
			return;
	}
}
