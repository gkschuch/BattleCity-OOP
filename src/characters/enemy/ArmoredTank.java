package characters.enemy;

import utils.Direction;
import utils.GameConfig;

public class ArmoredTank extends EnemyTank {

    // Construtor do tanque blindado
    // 4 vidas, 2 de velociade e 300 pontos
    public ArmoredTank(double x, double y) {
        super(x, y, GameConfig.LIVES_ARMORED, GameConfig.SPEED_ARMORED, GameConfig.SCORE_ARMORED);
    }

    // Implementa o comportamento do tanque blindado
    @Override
    public void updateIA() {
        // Depois trocar(para outro tipo de comportamento)
        // Existe a probabilidade de 50% de mudar de direção
        if (Math.random() > 0.5) {
            setDirection(Direction.getRandom());
        }
    }

    // Executa o disparo do tanque blindado
    @Override
    public void shoot() {
        // Na versao final cria um Projetil
        System.out.println("Armored Tank is shooting");
    }
}