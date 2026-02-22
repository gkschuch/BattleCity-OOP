package projectiles.strategy;

import projectiles.exceptions.InvalidProjectileAttributeException;
import utils.Direction;

public class PiercingMoveStrategy implements MoveStrategy {
    private int remainingPierces;

    public PiercingMoveStrategy() {
        this(2);
    }

    public PiercingMoveStrategy(int maxPierces) {
        if (maxPierces < 0)
            throw new InvalidProjectileAttributeException("maxPierces", maxPierces);
        this.remainingPierces = maxPierces;
    }

    @Override
    public int[] calculateNextPosition(int currentX, int currentY, Direction direction) {
        int newX = currentX + direction.getDx();
        int newY = currentY + direction.getDy();
        return new int[] { newX, newY };
    }

    @Override
    public boolean shouldContinueAfterHit() {
        if (remainingPierces > 0) {
            remainingPierces--;
            return true;
        }
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
        System.out.println("Penetracao! Restam " + remainingPierces + " penetracoes.");
    }

    public int getRemainingPierces() {
        return remainingPierces;
    }
}