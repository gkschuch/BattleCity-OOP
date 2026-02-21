package characters.enemy.strategy;

import characters.enemy.EnemyTank;
import characters.TankPlayer;
import grid.Grid;
import utils.Direction;

public class RandomBehaviorStrategy implements EnemyBehaviorStrategy {
    @Override
    public Direction determineDirection(EnemyTank tank, TankPlayer player, Grid grid) {
        if (Math.random() < 0.25)
            return Direction.getRandom();
        return tank.getDirection();
    }
}
