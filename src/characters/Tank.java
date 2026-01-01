package characters;

import utils.*;

public abstract class Tank implements Movable, Destructible {
    private int lives;
    private int speed;
    private Direction direction; // Direcao que o tanque esta apontando
    private boolean invulnerable;

    // Construtor do tanque
    public Tank(int lives, int speed) {
        this.lives = lives;
        this.speed = speed;
        this.direction = Direction.UP;
        this.invulnerable = false;
    }

    // Metodo abstrato do disparo
    // Cada subclasse implementa como o seu tanque atira
    public abstract void shoot();

    // Metodo que aplica o dano no tanque somente se ele nao for invulneravel
    @Override
    public void takeDamage(int damage) {
        if (!invulnerable) {
            int newLives = this.getLives() - damage;
            // Garante que nao seja um numero negativo
            this.setLives(Math.max(0, newLives));

            this.onDestroy();
        }
    }

    // Move para a direcao atual no grid
    // Fazer a movimetacao no grid
    public void move() {
        System.out.println("Tank is moving to: " + direction);
    }

    // Getters e Setters
    public int getLives() {
        return lives;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void toggleInvulnerability() {
        this.invulnerable = !this.invulnerable;
    }

    public boolean isInvulnerable() {
        return invulnerable;
    }

    public boolean isDestroyed() {
        return this.lives <= 0;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
