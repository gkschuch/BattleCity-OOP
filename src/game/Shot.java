package game;

import projectiles.BasicProjectile;

public class Shot {
	// atributos

	public final BasicProjectile p;
	public final boolean         fromEnemy;

	// construtor

	public Shot(BasicProjectile p, boolean fromEnemy) {
		this.p         = p;
		this.fromEnemy = fromEnemy;
	}

}
