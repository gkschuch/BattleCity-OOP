package characters;

public abstract class Tank {
    private int life;
    private int speed;
    private int gunLevel;
    private int tiago;

    // Construtor
    public Tank(int life, int speed, int gunLevel) {
        this.life = life;
        this.speed = speed;
        this.gunLevel = gunLevel;
    }

    // Getters e Setters

    public int getGunLevel() {
        return gunLevel;
    }

    public int getLife() {
        return life;
    }

    public int getSpeed() {
        return speed;
    }

    public void setGunLevel(int gunLevel) {
        this.gunLevel = gunLevel;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
