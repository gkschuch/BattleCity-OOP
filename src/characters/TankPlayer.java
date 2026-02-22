package characters;

import characters.exceptions.DependencyMissingException;
import grid.Grid;
import utils.Direction;
import utils.GameConfig;
import utils.SpriteManager;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

public class TankPlayer extends Tank {
	// atributos

	private final String playerName;
	private int score;
	private int gunLevel;
	private Grid grid;

	private final Map<Direction, BufferedImage> spriteCache;

	// contrutor

	public TankPlayer(String name, double x, double y, int lives, double speed) {
		super(x, y, speed, (lives >= GameConfig.MAX_LIVES) ? GameConfig.MAX_LIVES : lives);
		this.playerName = name;
		this.score = 0;
		this.gunLevel = 1;

		this.spriteCache = new EnumMap<>(Direction.class);
		loadSprites();
	}

	private void loadSprites() {
		BufferedImage spriteUp = SpriteManager.getSprite(304, 648, 125, 174);
		spriteCache.put(Direction.UP, spriteUp);
		spriteCache.put(Direction.DOWN, SpriteManager.flipVertically(spriteUp));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(517, 703, 178, 111));
		spriteCache.put(Direction.LEFT, SpriteManager.getSprite(971, 704, 184, 110));
	}

	// métodos

	@Override
	public void updateBehavior() {
	}

	@Override
	public BufferedImage getImage() {

		return spriteCache.get(this.getDirection());
	}

	@Override
	public void onDestroy() {
		System.out.println("Player " + this.getPlayerName() + " was destroyed!");
		if (this.getLives() <= 0) {
			System.out.println("GAME OVER - Final Score: " + this.getScore());
		} else {
			System.out.println("Lives remaining: " + this.getLives());
		}
	}

	public void addLives() {
		int currentLives = this.getLives();

		if (currentLives < GameConfig.MAX_LIVES) {
			this.setLives(currentLives + 1);
			System.out.println("Extra life obtained! Current lives: " + this.getLives());
		} else {
			this.addScore(GameConfig.SCORE_EXTRA_LIFE);
			System.out.println("Max lives reached! Bonus points awarded instead.");
		}
	}

	public void addScore(int points) {
		this.score += points;
	}

	public void upgradeGunLevel() {
		if (this.getGunLevel() < GameConfig.MAX_GUN_LEVEL) {
			this.gunLevel++;
		}
	}

	public int getScore() {
		return score;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getGunLevel() {
		return gunLevel;
	}

	public void setGrid(Grid grid) {
		if (grid == null)
			throw new DependencyMissingException("Grid no TankPlayer");
		this.grid = grid;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setGunLevel(int gunLevel) {
		this.gunLevel = gunLevel;
	}
}