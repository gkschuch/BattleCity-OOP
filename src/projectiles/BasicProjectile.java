package projectiles;

public class BasicProjectile extends Projectiles {
	// atributos

	private final int damage = 1;

	// contrutor

	public BasicProjectile(int startX, int startY, Direction direction) {
		super(startX, startY, direction);
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