package characters.enemy;

import characters.Tank;
import characters.TankPlayer;

public abstract class EnemyTank extends Tank {

	private final int scoreValue;
	private TankPlayer player;
	private boolean frozen;

	// contrutor

	public EnemyTank(double x, double y, int lives, int speed, int scoreValue, TankPlayer player) {
		super(x, y, speed, lives);
		this.scoreValue = scoreValue;
		this.player = player;
		this.frozen = false;
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

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	@Override
	public void onDestroy() {
		System.out.println("Enemy destroyed. Score: " + this.getScoreValue());
		player.addScore(this.getScoreValue());
	}
}