package characters.enemy;

import characters.Tank;

public abstract class EnemyTank extends Tank implements Runnable {
    // Valor dos pontos de cada tanque ao ser destruido
    private int scoreValue;

    // Construtor base dos tanques inimigos
    public EnemyTank(int lives, int speed, int scoreValue) {
        super(lives, speed); // Inicializa atributos na classe pai (Tank)
        this.scoreValue = scoreValue;
    }

    // Metodo abstrato para determinar o comportamento de cada tanque
    public abstract void updateIA();

    // Metodo que executa a thread
    // Controla o ciclo de vida do tanque independentemente
    @Override
    public void run() {
        // O tanque executa enquanto nao for destruido
        while (!isDestroyed()) {
            updateIA();
            move();
            shoot();
            try {
                // Ajusta o tempo de atualizacao da thread de acordo
                // com a velocidade do tanque
                Thread.sleep(1000 / getSpeed());
            } catch (InterruptedException e) {
                // Encerra a thread corretamente
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    // Metodo para mostrar quando o tanque e destruido
    // Mostra quantos pontos o jogador ganhou
    @Override
    public void onDestroy() {
        System.out.println("Enemy destroyed. Score: " + this.getScoreValue());
    }
}