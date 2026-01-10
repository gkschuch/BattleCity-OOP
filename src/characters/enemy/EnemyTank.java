package characters.enemy;

import characters.Tank;

public abstract class EnemyTank extends Tank {

    private final int scoreValue;

    // contrutor

    public EnemyTank(double x, double y, int lives, double speed, int scoreValue) {
        super(x, y, speed, lives);
        this.scoreValue = scoreValue;
    }

    // métodos

    public abstract void updateIA();

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    public void onDestroy() {
        System.out.println("Enemy destroyed. Score: " + scoreValue);
    }
}
