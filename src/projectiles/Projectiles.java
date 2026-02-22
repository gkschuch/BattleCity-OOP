package projectiles;

import game.InputController;
import grid.Grid;
import utils.Direction;
import projectiles.exceptions.MissingProjectileDependencyException;
import projectiles.exceptions.ProjectileException;
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
    protected InputController input;
    protected MoveStrategy moveStrategy; // Atributo para Strategy Pattern

    // construtores
    public Projectiles(int startX, int startY, Direction direction, int damage, InputController input) {
        if ((x < 0 || x > 13) || (y < 0 || y > 17))
            throw new ProjectileException("Coordenadas de spawn inválidas: " + x + "," + y);
        if (direction == null)
            throw new MissingProjectileDependencyException("Direction");
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.damage = damage;
        this.input = input;
        this.moveStrategy = new NormalMoveStrategy(); // Estratégia padrão
    }

    public Projectiles(int startX, int startY, Direction direction, MoveStrategy strategy, InputController input) {
        if ((x < 0 || x > 12) || (y < 0 || y > 16))
            throw new ProjectileException("Coordenadas de spawn inválidas: " + x + "," + y);
        if (direction == null)
            throw new MissingProjectileDependencyException("Direction");
        if (strategy == null)
            throw new MissingProjectileDependencyException("MoveStrategy");
        this.x = startX;
        this.y = startY;
        this.direction = direction;
        this.moveStrategy = strategy;
        this.input = input;
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
        if (this.grid == null)
            throw new MissingProjectileDependencyException("Grid (Use setGrid antes de chamar start)");
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (active) {
            if (input != null && input.isPaused()) {
                try {
                    Thread.sleep(100);
                    continue;
                } catch (InterruptedException e) {
                    if (!active)
                        break;
                }
            }

            move();

            if (grid != null) {
                if (!grid.isInside(y, x)) {
                    deactivate();
                    break;
                }

                boolean canContinue = grid.handleProjectileHit(y, x, getDamage());
                if (!canContinue) {
                    moveStrategy.onHit();
                    if (!moveStrategy.shouldContinueAfterHit()) {
                        deactivate();
                        break;
                    }
                }
            }

            try {
                Thread.sleep(moveStrategy.getMoveDelay());
            } catch (InterruptedException e) {
                if (!active)
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