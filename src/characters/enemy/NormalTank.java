package characters.enemy;

import utils.Direction;

public class NormalTank extends EnemyTank {

    // contrutor

    public NormalTank(double x, double y) {
        super(x, y, 1, 1.0, 100);
    }

    // métodos

    @Override
    public void updateIA() {
        if (Math.random() > 0.5)
            setDirection(Direction.getRandom());
    }

    @Override
    public void shoot() {
    }
}
