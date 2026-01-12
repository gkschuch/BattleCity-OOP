package characters.enemy;

import utils.Direction_enum;
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
		if ( Math.random() > 0.5 )
			setDirection(Direction_enum.getRandom());
	}

	@Override
	public void shoot() {
	}
}
