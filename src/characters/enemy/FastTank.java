package characters.enemy;

import utils.Direction;
import utils.GameConfig;
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
	public void shoot() {
		if (this.isFrozen())
			return;
	}
}
