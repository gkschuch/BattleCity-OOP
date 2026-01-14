package game;

import projectiles.BasicProjectile;
import utils.Direction;

public class ShotFactory {
	// construtor

	private ShotFactory() {
	}

	// métodos

	public static BasicProjectile createShotFromTank(double tankX, double tankY, Direction tankDir) {
		Direction pd = tankDir;

		int startX = (int) tankX;
		int startY = (int) tankY;

		return new BasicProjectile(startX, startY, pd, 1);
	}

}
