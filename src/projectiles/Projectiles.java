package projectiles;

import grid.Grid;

public abstract class Projectiles implements Runnable {

    // ATRIBUTOS (dados que todo projetil tem)
    protected int x; // coluna
    protected int y; // linha
    protected Direction direction; // direção do projétil

    protected boolean active = true;
    protected Thread thread;

    // ponte com o mapa
    protected Grid grid;

    // CONSTRUTOR
    public Projectiles(int startX, int startY, Direction direction) {
        this.x = startX;
        this.y = startY;
        this.direction = direction;
    }

    // liga o projétil ao Grid
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    // dano padrão (BasicProjectile sobrescreve com o próprio damage)
    public int getDamage() {
        return 1;
    }

    // inicia a thread
    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    // loop do projétil
    @Override
    public void run() {
        while (active) {
            move();

            // ponte: colisão/limites
            if (grid != null) {
                // Grid usa (row, col) = (y, x)
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

    // cada projétil implementa seu movimento
    protected abstract void move();

    public void deactivate() {
        active = false;
        if (thread != null)
            thread.interrupt();
    }

    // GETTERS
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
