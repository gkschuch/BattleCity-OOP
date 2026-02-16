package characters.enemy;

import characters.TankPlayer;
import utils.Direction;
import utils.GameConfig;

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
	public void shoot() {
		if (this.isFrozen())
			return;
	}

	public EnemyTankType getEnemyTankType() {
		return EnemyTankType.ARMORED;
	}
}
