package projectiles.strategy;

import utils.Direction;

public interface MoveStrategy {
    int[] calculateNextPosition(int currentX, int currentY, Direction direction);

    boolean shouldContinueAfterHit();

    int getBaseDamage();

    int getMoveDelay();

    void onHit();

    default String getStrategyName() {
        return this.getClass().getSimpleName();
    }
}