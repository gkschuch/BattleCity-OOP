package projectiles.strategy;

import projectiles.exceptions.MissingProjectileDependencyException;
import utils.Direction;

/**
 * Estratégia normal: movimento básico, para na primeira colisão.
 */
public class NormalMoveStrategy implements MoveStrategy {

    @Override
    public int[] calculateNextPosition(int currentX, int currentY, Direction direction) {
        if (direction == null)
            throw new MissingProjectileDependencyException("Direction para cálculo de rota");
        int newX = currentX + direction.getDx();
        int newY = currentY + direction.getDy();
        return new int[] { newX, newY };
    }

    @Override
    public boolean shouldContinueAfterHit() {
        return false;
    }

    @Override
    public int getBaseDamage() {
        return 1;
    }

    @Override
    public int getMoveDelay() {
        return 200;
    }

    @Override
    public void onHit() {
        System.out.println("Projétil normal atingiu alvo!");
    }
}