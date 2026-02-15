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
        // Move exatamente 1 célula na direção
        int newX = currentX + direction.getDx();
        int newY = currentY + direction.getDy();
        return new int[] { newX, newY };
    }

    @Override
    public boolean shouldContinueAfterHit() {
        return false; // Para imediatamente na colisão
    }

    @Override
    public int getBaseDamage() {
        return 1; // Dano básico
    }

    @Override
    public int getMoveDelay() {
        return 200; // Velocidade padrão: 200ms entre movimentos
    }

    @Override
    public void onHit() {
        // Comportamento padrão ao acertar
        System.out.println("Projétil normal atingiu alvo!");
    }
}