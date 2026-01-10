package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import characters.TankPlayer;

public class FastTank extends EnemyTank {

    // Construtor do tanque rapido
    // 2 vidas, 10 de velocidade e 150 pontos
    public FastTank(double x, double y, TankPlayer player) {
        super(x, y, GameConfig.LIVES_FAST, GameConfig.SPEED_FAST, GameConfig.SCORE_FAST, player);
    }

    // Implementa o comportamento do tanque rapido
    @Override
    public void updateIA() {
        // Depois trocar(para outro tipo de comportamento)
        // Existe a probabilidade de 50% de mudar de direção
        if (Math.random() > 0.5) {
            setDirection(Direction.getRandom());
        }
    }

    // Executa o disparo do tanque rapido
    @Override
    public void shoot() {
        // Na versao final cria um Projetil
        System.out.println("Fast Enemy Tank is shooting.");
    }
}