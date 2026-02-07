package characters;

import grid.Grid;
import projectiles.BasicProjectile;
import utils.Direction;
import utils.GameConfig;

public class TankPlayer extends Tank {
	// atributos

	private final String playerName;
	private int score;
	private int gunLevel;
	private Grid grid;
	private BasicProjectile lastShot;

	// contrutor

	public TankPlayer(String name, double x, double y, int lives, double speed) {
		super(x, y, speed, (lives >= GameConfig.MAX_LIVES) ? GameConfig.MAX_LIVES : lives);
		this.playerName = name;
		this.score = 0;
		this.gunLevel = 1;
	}

	// métodos

	@Override
	public void updateBehavior() {
		// comportamento automático do player na thread (pode ser vazio
		// ou usado para regenerar escudo/recarregar tiro) [cite: 102]
	}

	@Override
	public void shoot() {
		if (grid == null) {
			System.out.println("TankPlayer: grid nao configurado. Use player.setGrid(grid).");
			return;
		}

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
		this.grid = grid;
	}
}