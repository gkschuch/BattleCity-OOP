package characters.enemy;

import utils.Direction;
import utils.GameConfig;

public class NormalTank extends EnemyTank {

    // Construtor do tanque normal
    // 1 vida, 5 de velocidade e 100 pontos
    public NormalTank(double x, double y) {
        super(x, y, GameConfig.LIVES_NORMAL, GameConfig.SPEED_NORMAL, GameConfig.SCORE_NORMAL);
    }

    // Implementa o comportamento do tanque normal
    @Override
    public void updateIA() {
        // Existe a probabilidade de 50% de mudar de direção
        if (Math.random() > 0.5) {
            setDirection(Direction.getRandom());
        }
    }

    // Executa o disparo do tanque normal
    @Override
    public void shoot() {
        // Na versao final cria um Projetil
        System.out.println("Normal Tank is shooting.");
    }
}