package game;

import game.exceptions.MissingSystemDependencyException;
import projectiles.BasicProjectile;
import projectiles.strategy.MoveStrategy;
import utils.Direction;

public class ShotFactory {
	// construtor

	private ShotFactory() {
	}

	// métodos

	public static BasicProjectile createCustomShotFromTank(double tankX, double tankY,
			Direction tankDir, MoveStrategy strategy, InputController input) {
		if (tankDir == null)
			throw new MissingSystemDependencyException("ShotFactory", "Direction");
		if (strategy == null)
			throw new MissingSystemDependencyException("ShotFactory", "MoveStrategy");

		int startX = (int) tankX;
		int startY = (int) tankY;

		return new BasicProjectile(startX, startY, tankDir, strategy, input);
	}
}
