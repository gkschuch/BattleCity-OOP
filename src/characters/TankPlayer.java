package characters;

import grid.Grid;
import projectiles.BasicProjectile;
import ranking.RankingManager;
import utils.Direction_enum;
import utils.GameConfig;

public class TankPlayer extends Tank {
	// atributos

	private final String          playerName;
	private       int             score;
	private       int             gunLevel;
	private       Grid            grid;
	private       BasicProjectile lastShot;
	private       RankingManager  rankingManager;

	// contrutor

	public TankPlayer(String name, double x, double y, int lives, double speed, RankingManager rankingManager) {
		super(x, y, speed, (lives >= GameConfig.MAX_LIVES) ? GameConfig.MAX_LIVES : lives);
		this.playerName     = name;
		this.score          = 0;
		this.gunLevel       = 1;
		this.rankingManager = rankingManager;
	}

	// métodos

	@Override
	public void updateBehavior() {
		// comportamento automático do player na thread (pode ser vazio
		// ou usado para regenerar escudo/recarregar tiro) [cite: 102]
	}

	@Override
	public void shoot() {
		if ( grid == null ) {
			System.out.println("TankPlayer: grid nao configurado. Use player.setGrid(grid).");
			return;
		}

		if ( lastShot != null && lastShot.isActive() )
			return;

		projectiles.Direction pd = toProjectileDirection(this.getDirection());

		int startX = ( int ) this.getX() + pd.getDx();
		int startY = ( int ) this.getY() + pd.getDy();

		BasicProjectile p = new BasicProjectile(startX, startY, pd);
		p.setGrid(grid);
		p.start();

		lastShot = p;
	}

	private projectiles.Direction toProjectileDirection(Direction_enum d) {
		if ( d == Direction_enum.UP )
			return projectiles.Direction.UP;
		if ( d == Direction_enum.DOWN )
			return projectiles.Direction.DOWN;
		if ( d == Direction_enum.LEFT )
			return projectiles.Direction.LEFT;
		return projectiles.Direction.RIGHT;
	}

	@Override
	public void onDestroy() {
		System.out.println("Player " + this.getPlayerName() + " was destroyed!");
		if ( this.getLives() <= 0 ) {
			System.out.println("GAME OVER - Final Score: " + this.getScore());
		} else {
			System.out.println("Lives remaining: " + this.getLives());
		}
	}

	public void addScore(int points) {
		this.score += points;
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