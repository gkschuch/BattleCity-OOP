package projectiles.strategy;

import utils.Direction;

/**
 * Interface que define a estratégia de movimento para projéteis.
 * Padrão Strategy: permite diferentes comportamentos de movimento
 * sem alterar as classes de projétil.
 */
public interface MoveStrategy {

    /**
     * Calcula a próxima posição do projétil baseada na direção atual.
     *
     * @param currentX posição X atual
     * @param currentY posição Y atual
     * @param direction direção do movimento
     * @return array com nova posição [x, y]
     */
    int[] calculateNextPosition(int currentX, int currentY, Direction direction);

    /**
     * Determina se o projétil deve continuar se movendo após colidir.
     *
     * @return true para continuar, false para parar
     */
    boolean shouldContinueAfterHit();

    /**
     * Retorna o dano base do projétil com esta estratégia.
     *
     * @return valor do dano
     */
    int getBaseDamage();

    /**
     * Retorna o tempo de espera entre movimentos (em milissegundos).
     * Controla a velocidade do projétil.
     *
     * @return delay em ms
     */
    int getMoveDelay();

    /**
     * Ação executada quando o projétil atinge algo.
     * Pode ser usado para efeitos especiais, som, etc.
     */
    void onHit();

    /**
     * Retorna o nome da estratégia (para debug e identificação).
     *
     * @return nome da estratégia
     */
    default String getStrategyName() {
        return this.getClass().getSimpleName();
    }
}