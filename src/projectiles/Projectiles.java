package projectiles;

import grid.Grid;
import utils.Direction;

public abstract class Projectiles implements Runnable {
	// atributos

	protected int x;
	protected int y;
	protected int damage;
	protected Direction direction;
	protected boolean active = true;
	protected Thread thread;
	protected Grid grid;

	// contrutor

	public Projectiles(int startX, int startY, Direction direction, int damage) {
		this.x = startX;
		this.y = startY;
		this.direction = direction;
		this.damage = damage;
	}

	// métodos

	protected abstract void move();

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (active) {
			move();

			if (grid != null) {
				if (!grid.isInside(y, x)) {
					deactivate();
					break;
				}

				boolean canContinue = grid.handleProjectileHit(y, x, getDamage());
				if (!canContinue) {
					deactivate();
					break;
				}
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void deactivate() {
		active = false;
		if (thread != null)
			thread.interrupt();
	}

	// métodos especiais (getters e setters)

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public int getDamage() {
		return damage;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isActive() {
		return active;
	}

	public Direction getDirection() {
		return direction;
	}
}
