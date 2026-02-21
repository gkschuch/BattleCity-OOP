package characters.enemy.strategy;

import characters.enemy.EnemyTank;
import characters.TankPlayer;
import grid.Grid;
import utils.Direction;

public class PersuitBehaviorStrategy implements EnemyBehaviorStrategy {
    @Override
    public Direction determineDirection(EnemyTank tank, TankPlayer player, Grid grid) {
        int targetX = (int) player.getX();
        int targetY = (int) player.getY();
        int myX = (int) tank.getX();
        int myY = (int) tank.getY();

        if (Math.random() < 0.10)
            return Direction.getRandom();

        Direction primary = (Math.abs(targetX - myX) > Math.abs(targetY - myY))
                ? (targetX > myX ? Direction.RIGHT : Direction.LEFT)
                : (targetY > myY ? Direction.DOWN : Direction.UP);

        Direction secondary = (primary == Direction.RIGHT || primary == Direction.LEFT)
                ? (targetY > myY ? Direction.DOWN : Direction.UP)
                : (targetX > myX ? Direction.RIGHT : Direction.LEFT);

        if (grid.isWalkable(myY + primary.getDy(), myX + primary.getDx())) {
            return primary;
        }
        if (grid.isWalkable(myY + secondary.getDy(), myX + secondary.getDx())) {
            return secondary;
        }
        return tank.getDirection();
    }
}
