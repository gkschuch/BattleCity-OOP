package characters;

import utils.*;

public abstract class Tank implements Movable, Destructible {
    // atributos
    
    private volatile int lives;
    private double speed;
    private Direction direction;
    private boolean invulnerable;
    private double x, y;

    //contrutor

    public Tank(double x, double y, double speed, int lives) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = Direction.UP;
        this.lives = lives;
    }

    // métodos

    public abstract void shoot();

    @Override
    public void takeDamage(int damage) {
        if (!invulnerable) {
            int newLives = this.getLives() - damage;
            this.setLives(Math.max(0, newLives));

            if (isDestroyed()) {
                this.onDestroy();
            }
        }
    }

    @Override
    public void move() {
        this.x += direction.getDx() * speed;
        this.y += direction.getDy() * speed;

        System.out.println("Moving to: " + x + ", " + y);
    }

    @Override
    public boolean isDestroyed() {
        return this.lives <= 0;
    }

    public void toggleInvulnerability() {
        this.invulnerable = !this.invulnerable;
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
        this.lives = lives;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
}
