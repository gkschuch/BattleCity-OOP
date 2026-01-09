package characters.enemy;

import utils.Direction;

public class ArmedTank extends EnemyTank {

    // contrutor

    public ArmedTank(double x, double y) {
        super(x, y, 2, 1.0, 200);
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
