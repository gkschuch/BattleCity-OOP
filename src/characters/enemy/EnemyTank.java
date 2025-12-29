package characters.enemy;

import characters.Tank;

public abstract class EnemyTank extends Tank implements Runnable {
    private int scoreValue;

    public EnemyTank(int lives, int speed, int scoreValue) {
        super(lives, speed);
        this.scoreValue = scoreValue;
    }

    // Metodo para o comportamento do Tanque
    public abstract void updateIA();

    @Override
    public void run() {
        while (!isDestroyed()) {
            updateIA();
            move();
            shoot();
            try {
                Thread.sleep(1000 / getSpeed());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    @Override
    public void onDestroy() {
        System.out.println("Enemy destroyed. Score: " + getScoreValue());
    }

}
