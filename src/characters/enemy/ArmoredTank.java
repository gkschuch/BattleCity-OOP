package characters.enemy;

import utils.Direction;

public class ArmoredTank extends EnemyTank {

    // contrutor

    public ArmoredTank(double x, double y) {
        super(x, y, 4, 1.0, 300);
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
