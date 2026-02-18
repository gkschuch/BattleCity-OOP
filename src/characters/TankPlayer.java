package characters;

import characters.exceptions.DependencyMissingException;
import grid.Grid;
import projectiles.BasicProjectile;
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
	private BasicProjectile lastShot;

	// CACHE DE IMAGENS: Guarda as sprites já carregadas para não recriar
	// Usamos EnumMap pois é muito mais rápido que HashMap para Enums
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
		spriteCache.put(Direction.UP, SpriteManager.getSprite(304, 648, 125, 174));
		spriteCache.put(Direction.RIGHT, SpriteManager.getSprite(517, 703, 178, 111));
		spriteCache.put(Direction.DOWN, SpriteManager.getSprite(770, 848, 125, 174));
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
	public void shoot() {
		if (grid == null)
			throw new DependencyMissingException("Grid");

		if (lastShot != null && lastShot.isActive())
			return;

		Direction pd = this.getDirection();

		int startX = (int) this.getX() + pd.getDx();
		int startY = (int) this.getY() + pd.getDy();

		BasicProjectile p = new BasicProjectile(startX, startY, pd, getGunLevel(), null);
		p.setGrid(grid);
		p.start();

		lastShot = p;
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