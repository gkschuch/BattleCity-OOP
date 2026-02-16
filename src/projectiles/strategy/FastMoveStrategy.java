package projectiles.strategy;

import projectiles.exceptions.MissingProjectileDependencyException;
import utils.Direction;

/**
 * Estratégia rápida: se move mais rapidamente.
 */
public class FastMoveStrategy implements MoveStrategy {

    @Override
    public int[] calculateNextPosition(int currentX, int currentY, Direction direction) {
        if (direction == null)
            throw new MissingProjectileDependencyException("Direction para cálculo de rota");
        // Move 1 célula (poderia ser mais rápido com multiplicador)
        int newX = currentX + direction.getDx();
        int newY = currentY + direction.getDy();
        return new int[] { newX, newY };
    }

    @Override
    public boolean shouldContinueAfterHit() {
        return false; // Para na colisão
    }

    @Override
    public int getBaseDamage() {
        return 1; // Dano normal
    }

    @Override
    public int getMoveDelay() {
        return 100; // MAIS RÁPIDO: 100ms entre movimentos
    }

    @Override
    public void onHit() {
        System.out.println("Projétil rápido atingiu alvo!");
    }
}