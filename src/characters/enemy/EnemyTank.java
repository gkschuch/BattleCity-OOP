package characters.enemy;

import characters.Tank;
import characters.TankPlayer;

public abstract class EnemyTank extends Tank {

	private final int        scoreValue;
	private       TankPlayer player;

	// contrutor

	public EnemyTank(double x, double y, int lives, int speed, int scoreValue, TankPlayer player) {
		super(x, y, speed, lives);
		this.scoreValue = scoreValue;
		this.player     = player;
	}

	// métodos

	@Override
	public void updateBehavior() {
		this.updateIA();
	}

	public abstract void updateIA();

	public int getScoreValue() {
		return scoreValue;
	}

	@Override
	public void onDestroy() {
		System.out.println("Enemy destroyed. Score: " + this.getScoreValue());
		player.addScore(this.getScoreValue());
	}
}