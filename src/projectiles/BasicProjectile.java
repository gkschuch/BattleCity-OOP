package projectiles;

import utils.Direction;

public class BasicProjectile extends Projectiles {
	// contrutor

	public BasicProjectile(int startX, int startY, Direction direction, int damage) {
		super(startX, startY, direction, damage);
	}

	// métodos

	@Override
	protected void move() {
		x += direction.getDx();
		y += direction.getDy();
	}

	@Override
	public int getDamage() {
		return damage;
	}

	public boolean checkCollision(int targetX, int targetY) {
		return this.x == targetX && this.y == targetY;
	}
}