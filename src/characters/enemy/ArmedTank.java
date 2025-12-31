package characters.enemy;

import utils.Direction;

public class ArmedTank extends EnemyTank {

    // Construtor do tanque armado
    // 2 vidas, 5 de velocidade e 200 pontos
    public ArmedTank() {
        super(2, 5, 200);
    }

    // Implementa o comportamento do Tanque Armado(Aleatorio)
    @Override
    public void updateIA() {
        // Depois trocar(para outro tipo de comportamento)
        // Existe a probabilidade de 50% de mudar de direção
        if (Math.random() > 0.5) {
            setDirection(Direction.getRandom());
        }
    }

    // Executa o dispado do tanque
    @Override
    public void shoot() {
        // Na versao final cria um Projetil
        System.out.println("Armed Enemy Tank is shootings.");
    }
}