package characters.enemy;

import characters.Tank;
import characters.TankPlayer;

public abstract class EnemyTank extends Tank implements Runnable {
    // Valor dos pontos de cada tanque ao ser destruido
    private int scoreValue;
    private TankPlayer player;

    // Construtor base dos tanques inimigos
    public EnemyTank(double x, double y, int lives, int speed, int scoreValue, TankPlayer player) {
        super(x, y, lives, speed); // Inicializa atributos na classe pai (Tank)
        this.scoreValue = scoreValue;
        this.player = player;
    }

    // Metodo abstrato para determinar o comportamento de cada tanque
    public abstract void updateIA();

    // Metodo que executa a thread
    // Controla o ciclo de vida do tanque independentemente
    @Override
    public void run() {
        String tankId = this.getClass().getSimpleName() + "@" + System.identityHashCode(this);
        try {
            while (!isDestroyed() && !Thread.currentThread().isInterrupted()) {
                updateIA();
                move();
                shoot();
                System.out.println("thread active:" + tankId + "in x:" + getX());
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(">>> THREAD ENCERRADA: " + tankId);
        onDestroy();
    }

    public int getScoreValue() {
        return scoreValue;
    }

    // Metodo para mostrar quando o tanque e destruido
    // Mostra quantos pontos o jogador ganhou
    @Override
    public void onDestroy() {
        System.out.println("Enemy destroyed. Score: " + this.getScoreValue());
        player.addScore(this.getScoreValue());
    }
}