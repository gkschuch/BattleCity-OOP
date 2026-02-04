package projectiles;

import grid.Grid;
import utils.Direction;
import projectiles.strategy.MoveStrategy;
import projectiles.strategy.NormalMoveStrategy;

public abstract class Projectiles implements Runnable {
    // atributos
    protected int x;
    protected int y;
    protected int damage;
    protected Direction direction;
    protected boolean active = true;
    protected Thread thread;
    protected Grid grid;
    protected MoveStrategy moveStrategy; // Atributo para Strategy Pattern

    // construtores
    public Projectiles(int startX, int startY, Direction direction, int damage) {
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.damage = damage;
        this.moveStrategy = new NormalMoveStrategy(); // Estratégia padrão
    }

    public Projectiles(int startX, int startY, Direction direction, MoveStrategy strategy) {
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.moveStrategy = strategy;
        this.damage = strategy.getBaseDamage(); // Dano baseado na estratégia
    }

    // métodos
    protected void move() {
        // Usa a estratégia para calcular movimento
        int[] newPos = moveStrategy.calculateNextPosition(x, y, direction);
        x = newPos[0];
        y = newPos[1];
    }

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
                    moveStrategy.onHit(); // Executa ação da estratégia
                    if (!moveStrategy.shouldContinueAfterHit()) {
                        deactivate();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(moveStrategy.getMoveDelay()); // Delay baseado na estratégia
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

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }
}