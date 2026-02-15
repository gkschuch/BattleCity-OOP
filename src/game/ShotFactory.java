package game;

import game.exceptions.InvalidShotParameterException;
import game.exceptions.MissingSystemDependencyException;
import projectiles.BasicProjectile;
import utils.Direction;

public class ShotFactory {
	// construtor

	private ShotFactory() {
	}

	// métodos

	public static BasicProjectile createShotFromTank(double tankX, double tankY,
			Direction tankDir, int damage, InputController input) {
		if (tankDir == null)
			throw new MissingSystemDependencyException("ShotFactory", "Direction (A direção do tanque é nula)");

		if (damage <= 0)
			throw new InvalidShotParameterException("damage", damage);
		Direction pd = tankDir;

		int startX = (int) tankX;
		int startY = (int) tankY;

		return new BasicProjectile(startX, startY, pd, damage, input);
	}

}
