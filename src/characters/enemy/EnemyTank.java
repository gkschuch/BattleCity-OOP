package characters.enemy;

import characters.Tank;
import characters.TankPlayer;
import characters.exceptions.DependencyMissingException;
import grid.Grid;
import utils.Direction;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public abstract class EnemyTank extends Tank {

	private final int scoreValue;
	private TankPlayer player;
	private Grid grid;
	private boolean frozen;

	protected final Map<Direction, BufferedImage> spriteCache;

	// construtor
	public EnemyTank(double x, double y, int lives, int speed, int scoreValue, TankPlayer player) {
		super(x, y, speed, lives);
		this.scoreValue = scoreValue;
		this.player = player;
		this.frozen = false;

		this.spriteCache = new EnumMap<>(Direction.class);

		loadSprites();
	}

	protected abstract void loadSprites();

	// métodos

	@Override
	public void updateBehavior() {
		if (isDestroyed())
			return;
		this.updateIA();
	}

	public abstract void updateIA();

	@Override
	public BufferedImage getImage() {
		return spriteCache.get(this.getDirection());
	}

	public abstract EnemyTankType getEnemyTankType();

	public int getScoreValue() {
		return scoreValue;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public void setGrid(Grid grid) {
		if (grid == null)
			throw new DependencyMissingException("Grid no EnemyTank");
		this.grid = grid;
	}

	@Override
	public void onDestroy() {
		System.out.println("Enemy destroyed. Score: " + this.getScoreValue());
		player.addScore(this.getScoreValue());
	}
}