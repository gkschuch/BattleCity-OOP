package characters.enemy;

import utils.Direction;

public class FastTank extends EnemyTank {

    // contrutor

    public FastTank(double x, double y) {
        super(x, y, 2, 1.0, 150);
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
