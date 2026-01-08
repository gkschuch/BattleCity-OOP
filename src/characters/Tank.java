package characters;

import utils.*;

public abstract class Tank implements Movable, Destructible {
    private volatile int lives;
    private double speed;
    private Direction direction;
    private boolean invulnerable;
    private double x, y;

    // Construtor do tanque
    public Tank(double x, double y, double speed, int lives) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = Direction.UP;
        this.lives = lives;
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

            if (isDestroyed()) {
                this.onDestroy();
            }
        }
    }

    // Move para a direcao atual no grid
    // Fazer a movimetacao no grid
    public void move() {
        // A nova posição é a atual + (direção * velocidade)
        this.x += direction.getDx() * speed;
        this.y += direction.getDy() * speed;

        System.out.println("Moving to: " + x + ", " + y);
    }

    // Getters e Setters
    public int getLives() {
        return lives;
    }

    public double getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
