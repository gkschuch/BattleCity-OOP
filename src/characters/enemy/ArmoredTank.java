package characters.enemy;

import characters.TankPlayer;
import utils.Direction_enum;
import utils.GameConfig;

public class ArmoredTank extends EnemyTank {

	// contrutor

	public ArmoredTank(double x, double y, TankPlayer player) {
		super(x, y, GameConfig.LIVES_ARMORED, GameConfig.SPEED_ARMORED, GameConfig.SCORE_ARMORED, player);
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
