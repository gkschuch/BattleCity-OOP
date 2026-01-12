package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;

import java.util.List;

import projectiles.BasicProjectile;

public class CollisionSystem {
	// construtor

	private CollisionSystem() {
	}

	// métodos

	public static void handleShotsVsTanks(List<Shot> shots, List<EnemyTank> enemies, TankPlayer player) {
		if ( shots == null || enemies == null || player == null )
			return;

		synchronized ( shots ) {
			for ( int i = 0; i < shots.size(); i++ ) {
				Shot s = shots.get(i);

				if ( s == null || s.p == null )
					continue;

				BasicProjectile p = s.p;

				if ( !p.isActive() )
					continue;

				int r = p.getY();
				int c = p.getX();

				if ( s.fromEnemy ) {
					if ( (( int ) player.getY() == r) && (( int ) player.getX() == c) ) {
						player.takeDamage(p.getDamage());
						p.deactivate();
					}
				} else {
					for ( int j = 0; j < enemies.size(); j++ ) {
						EnemyTank e = enemies.get(j);

						if ( e == null )
							continue;

						if ( e.isDestroyed() )
							continue;

						if ( (( int ) e.getY() == r) && (( int ) e.getX() == c) ) {
							e.takeDamage(p.getDamage());
							p.deactivate();

							if ( e.isDestroyed() ) {
								player.addScore(e.getScoreValue());
							}
							break;
						}
					}
				}
			}
		}
	}
}
