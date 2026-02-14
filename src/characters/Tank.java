package characters;

import characters.exceptions.InvalidAttributeException;
import characters.exceptions.TankDestroyedException;
import utils.*;

public abstract class Tank implements Movable, Destructible, Runnable {
	// atributos

	private int lives;
	private double speed;
	private Direction direction;
	private boolean invulnerable;
	private double x, y;
	protected Thread thread;
	protected volatile boolean active = false;

	// contrutor

	public Tank(double x, double y, double speed, int lives) {
		if (speed <= 0)
			throw new InvalidAttributeException("Speed", speed);
		if (lives <= 0)
			throw new InvalidAttributeException("Lives", lives);
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.direction = Direction.UP;
		this.lives = lives;
	}

	// métodos

	public abstract void shoot();

	public abstract void updateBehavior();

	public void start() {
		if (!active) {
			active = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop() {
		active = false;
		if (thread != null) {
			thread.interrupt();
		}
	}

	@Override
	public void run() {
		while (active && !isDestroyed()) {
			updateBehavior();

			try {
				long sleepTime = (long) (1000 / Math.max(1, speed));
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		active = false;
	}

	@Override
	public void takeDamage(int damage) {
		if (this.invulnerable)
			return;
		int newLives = this.getLives() - damage;
		this.setLives(Math.max(0, newLives));

		if (!this.isDestroyed())
			return;

		this.onDestroy();
		this.stop();
	}

	@Override
	public void move() {
		if (isDestroyed()) {
			throw new TankDestroyedException("move");
		}
		this.x += direction.getDx() * speed;
		this.y += direction.getDy() * speed;

		System.out.println("Moving to: " + x + ", " + y);
	}

	@Override
	public boolean isDestroyed() {
		return this.lives <= 0;
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	// métodos especiais (getters e setters)

	public int getLives() {
		return lives;
	}

	public double getSpeed() {
		return speed;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setLives(int lives) {
		if (lives <= 0)
			throw new InvalidAttributeException("Lives", lives);
		this.lives = lives;
	}

	public void setSpeed(double speed) {
		if (speed <= 0)
			throw new InvalidAttributeException("Speed", speed);
		this.speed = speed;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}