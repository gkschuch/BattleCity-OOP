package characters.enemy.strategy;

import characters.enemy.EnemyTank;
import characters.TankPlayer;
import grid.Grid;
import utils.Direction;

public class FleeBehaviorStrategy implements EnemyBehaviorStrategy {
    @Override
    public Direction determineDirection(EnemyTank tank, TankPlayer player, Grid grid) {
        int targetX = (int) player.getX();
        int targetY = (int) player.getY();
        int myX = (int) tank.getX();
        int myY = (int) tank.getY();

        if (Math.random() < 0.10)
            return Direction.getRandom();

        Direction primary = (Math.abs(targetX - myX) > Math.abs(targetY - myY))
                ? (targetX > myX ? Direction.LEFT : Direction.RIGHT)
                : (targetY > myY ? Direction.UP : Direction.DOWN);

        Direction secondary = (primary == Direction.RIGHT || primary == Direction.LEFT)
                ? (targetY > myY ? Direction.UP : Direction.DOWN)
                : (targetX > myX ? Direction.LEFT : Direction.RIGHT);

        if (grid.isWalkable(myY + primary.getDy(), myX + primary.getDx())) {
            return primary;
        }

        if (grid.isWalkable(myY + secondary.getDy(), myX + secondary.getDx())) {
            return secondary;
        }

        Direction current = tank.getDirection();
        if (grid.isWalkable(myY + current.getDy(), myX + current.getDx())) {
            return current;
        }

        return Direction.getRandom();
    }
}
