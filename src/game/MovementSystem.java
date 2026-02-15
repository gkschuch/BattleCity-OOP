package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import game.exceptions.MissingSystemDependencyException;
import grid.Grid;
import grid.blocks.Block;
import utils.Direction;

import java.util.List;

public class MovementSystem {
	// construtor

	private MovementSystem() {
	}

	// métodos

	public static void tryMovePlayer(Grid grid, TankPlayer p, Direction dir, List<EnemyTank> enemies) {
		if (grid == null)
			throw new MissingSystemDependencyException("MovementSystem", "grid");
		if (enemies == null)
			throw new MissingSystemDependencyException("MovementSystem", "List<EnemyTank>");
		if (p == null)
			throw new MissingSystemDependencyException("MovementSystem", "TankPlayer");
		if (dir == null)
			throw new MissingSystemDependencyException("MovementSystem", "Direction");

		p.setDirection(dir);

		int row = (int) p.getY();
		int col = (int) p.getX();

		int newRow = row + dir.getDy();
		int newCol = col + dir.getDx();

		if (!grid.isInside(newRow, newCol))
			return;

		if (!grid.isWalkable(newRow, newCol))
			return;

		if (isEnemyAt(enemies, newRow, newCol))
			return;

		Block targetBlock = grid.getBlock(newRow, newCol);
		if (targetBlock != null) {
			targetBlock.onPlayerStep(p, grid);
		}

		p.setY(newRow);
		p.setX(newCol);
	}

	public static void stepEnemy(Grid grid, EnemyTank e, TankPlayer player, List<EnemyTank> enemies) {
		if (grid == null)
			throw new MissingSystemDependencyException("MovementSystem", "grid");
		if (e == null)
			throw new MissingSystemDependencyException("MovementSystem", "EnemyTank");
		if (enemies == null)
			throw new MissingSystemDependencyException("MovementSystem", "List<EnemyTank>");
		if (player == null)
			throw new MissingSystemDependencyException("MovementSystem", "TankPlayer");

		int row = (int) e.getY();
		int col = (int) e.getX();

		Direction dir = e.getDirection();
		int newRow = row + dir.getDy();
		int newCol = col + dir.getDx();

		if (!grid.isInside(newRow, newCol) || !grid.isWalkable(newRow, newCol)) {
			e.setDirection(Direction.getRandom());
			return;
		}

		if (isEnemyAt(enemies, newRow, newCol))
			return;

		if (isPlayerAt(player, newRow, newCol)) {
			player.takeDamage(1);
			return;
		}
		Block targetBlock = grid.getBlock(newRow, newCol);
		if (targetBlock != null) {
			targetBlock.onEnemyStep(e, grid);
		}
		e.setY(newRow);
		e.setX(newCol);
	}

	public static boolean isPlayerAt(TankPlayer p, int row, int col) {
		if (p == null)
			return false;
		return ((int) p.getY() == row && (int) p.getX() == col);
	}

	public static boolean isEnemyAt(List<EnemyTank> enemies, int row, int col) {
		if (enemies == null)
			return false;
		for (int i = 0; i < enemies.size(); i++) {
			EnemyTank e = enemies.get(i);

			if (e == null)
				continue;

			if (e.isDestroyed())
				continue;

			if ((int) e.getY() == row && (int) e.getX() == col)
				return true;
		}

		return false;
	}

	public static int countAlive(List<EnemyTank> enemies) {
		if (enemies == null)
			throw new MissingSystemDependencyException("MovementSystem (countAlive)", "List<EnemyTank>");
		int n = 0;

		for (int i = 0; i < enemies.size(); i++) {
			EnemyTank e = enemies.get(i);

			if (e == null)
				continue;

			if (!e.isDestroyed())
				n++;
		}
		return n;
	}
}
