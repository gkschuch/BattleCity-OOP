package characters.enemy;

import utils.Direction;

public class NormalTank extends EnemyTank {
    public NormalTank() {
        super(1, 5, 100);
    }

    @Override
    public void updateIA() {
        // Logica de movimento aleatorio
        if (Math.random() > 0.5) {
            setDirection(Direction.getRandom());
        }
    }

    @Override
    public void shoot() {
        // Na versao final cria o objeto projetil
        System.out.println("Normal Tank is shooting.");
    }
}
