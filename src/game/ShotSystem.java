package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import grid.Grid;

import java.util.Iterator;
import java.util.List;

import projectiles.BasicProjectile;
import projectiles.strategy.FastMoveStrategy;
import projectiles.strategy.MoveStrategy;
import projectiles.strategy.NormalMoveStrategy;
import projectiles.strategy.PiercingMoveStrategy;

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
		MoveStrategy strategy = new NormalMoveStrategy();

		BasicProjectile p = ShotFactory.createCustomShotFromTank(
				player.getX(),
				player.getY(),
				player.getDirection(),
				strategy,
				input);
		p.setGrid(grid);
		p.start();

		shots.add(new Shot(p, false));
	}

	public static void enemiesRandomShoot(List<Shot> shots, Grid grid,
			List<EnemyTank> enemies, double chancePerTick, InputController input, int difficulty) {

		for (EnemyTank e : enemies) {
			if (e == null || e.isFrozen() || e.isDestroyed())
				continue;

			if (Math.random() < chancePerTick) {
				MoveStrategy strategy;

				if (difficulty <= 1) {
					strategy = new NormalMoveStrategy();
				} else {
					strategy = switch (e.getEnemyTankType()) {
						case ARMED -> new PiercingMoveStrategy(2);
						case FAST -> new FastMoveStrategy();
						case ARMORED, NORMAL -> new NormalMoveStrategy();
					};
				}

				BasicProjectile p = ShotFactory.createCustomShotFromTank(
						e.getX(), e.getY(), e.getDirection(), strategy, input);

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
