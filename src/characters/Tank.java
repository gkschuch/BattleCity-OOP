package characters;

import utils.*;

public abstract class Tank implements Movable, Destructible {
    private int lives; // O numero de vidas do tanque
    private int speed;
    private Direction direction;
    private boolean invulnerable;

    // Construtor
    public Tank(int lives, int speed) {
        this.lives = lives;
        this.speed = speed;
        this.direction = Direction.UP;
        this.invulnerable = false;
    }

    public abstract void shoot();

    // Logica para quando tomar dano
    @Override
    public void takeDamege(int damege) {
        if (!invulnerable) {
            this.lives -= damege;
            if (isDestroyed()) {
                this.onDestroy();
            }
        }
    }

    // Adiciona o movimento do tanque
    public void move() {
        System.out.println("Tank is moving to: " + direction);
    }

    // Getters e Setters

    public int getlives() {
        return lives;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setInvulnerable(boolean invulnerable) {
        this.invulnerable = invulnerable;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isDestroyed() {
        return this.lives <= 0;
    }

    public void setlives(int lives) {
        this.lives = lives;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
