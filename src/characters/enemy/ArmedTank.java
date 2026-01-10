package characters.enemy;

import utils.Direction;
import utils.GameConfig;
import characters.TankPlayer;

public class ArmedTank extends EnemyTank {

    // contrutor

    public ArmedTank(double x, double y, TankPlayer player) {
        super(x, y, GameConfig.LIVES_ARMED, GameConfig.SPEED_ARMED, GameConfig.SCORE_ARMED, player);
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
