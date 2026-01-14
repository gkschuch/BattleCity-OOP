package projectiles.strategy;

import utils.Direction;

/**
 * Estratégia perfurante: atravessa múltiplos alvos.
 */
public class PiercingMoveStrategy implements MoveStrategy {
    private int remainingPierces;

    public PiercingMoveStrategy() {
        this(2); // Padrão: atravessa 2 vezes
    }

    public PiercingMoveStrategy(int maxPierces) {
        this.remainingPierces = maxPierces;
    }

    @Override
    public int[] calculateNextPosition(int currentX, int currentY, Direction direction) {
        // Move 1 célula (igual ao normal)
        int newX = currentX + direction.getDx();
        int newY = currentY + direction.getDy();
        return new int[]{newX, newY};
    }

    @Override
    public boolean shouldContinueAfterHit() {
        if (remainingPierces > 0) {
            remainingPierces--;
            return true; // Continua após colisão
        }
        return false; // Para quando acabarem as penetracoes
    }

    @Override
    public int getBaseDamage() {
        return 1; // Dano normal
    }

    @Override
    public int getMoveDelay() {
        return 200; // Velocidade normal
    }

    @Override
    public void onHit() {
        System.out.println("Penetracao! Restam " + remainingPierces + " penetracoes.");
    }

    // Método adicional específico desta estratégia
    public int getRemainingPierces() {
        return remainingPierces;
    }
}