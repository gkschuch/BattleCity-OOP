package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import grid.Grid;

import java.util.Iterator;
import java.util.List;

import projectiles.BasicProjectile;

public class ShotSystem {
	// construtor

	private ShotSystem() {
	}

	// métodos

	public static void playerShoot(List<Shot> shots, Grid grid, TankPlayer player, boolean paused,
			InputController input) {
		if (input == null)
			return;

		if (paused)
			return;
		BasicProjectile p = ShotFactory.createShotFromTank(player.getX(), player.getY(), player.getDirection(),
				player.getGunLevel(), input);
		p.setGrid(grid);
		p.start();

		shots.add(new Shot(p, false));
	}

	public static void enemiesRandomShoot(List<Shot> shots, Grid grid,
			List<EnemyTank> enemies, double chancePerTick, InputController input) {
		for (int i = 0; i < enemies.size(); i++) {
			EnemyTank e = enemies.get(i);

			if (e == null)
				continue;
			if (e.isFrozen())
				continue;

			if (e.isDestroyed())
				continue;

			if (Math.random() < chancePerTick) {
				BasicProjectile p = ShotFactory.createShotFromTank(e.getX(), e.getY(), e.getDirection(), 1, input);
				p.setGrid(grid);
				p.start();

				shots.add(new Shot(p, true));
			}
		}
	}

	public static void cleanupShots(List<Shot> shots) {
		synchronized (shots) {
			Iterator<Shot> it = shots.iterator();

			while (it.hasNext()) {
				Shot s = it.next();

				if (s == null || s.p == null || !s.p.isActive())
					it.remove();
			}
		}
	}

	public static void stopAllShots(List<Shot> shots) {
		synchronized (shots) {
			for (Shot s : shots) {
				if (s != null && s.p != null && s.p.isActive())
					s.p.deactivate();
			}

			shots.clear();
		}
	}

}
