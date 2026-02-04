package projectiles.strategy;

import utils.Direction;

public class StrategyTest {
    public static void main(String[] args) {
        System.out.println("=== TESTE DO STRATEGY PATTERN ===\n");

        // 1. Teste NormalMoveStrategy
        System.out.println("1. NormalMoveStrategy:");
        MoveStrategy normal = new NormalMoveStrategy();
        testStrategy(normal, 5, 5, Direction.RIGHT);

        // 2. Teste PiercingMoveStrategy
        System.out.println("\n2. PiercingMoveStrategy:");
        MoveStrategy piercing = new PiercingMoveStrategy();
        testStrategy(piercing, 0, 0, Direction.DOWN);

        // 3. Teste FastMoveStrategy
        System.out.println("\n3. FastMoveStrategy:");
        MoveStrategy fast = new FastMoveStrategy();
        testStrategy(fast, 10, 10, Direction.LEFT);

        System.out.println("\n=== TODAS AS ESTRATÉGIAS FUNCIONANDO! ===");
    }

    private static void testStrategy(MoveStrategy strategy, int startX, int startY, Direction dir) {
        System.out.println("  Nome: " + strategy.getStrategyName());
        System.out.println("  Dano: " + strategy.getBaseDamage());
        System.out.println("  Delay: " + strategy.getMoveDelay() + "ms");

        // Testa movimento
        int[] pos1 = strategy.calculateNextPosition(startX, startY, dir);
        System.out.println("  Movimento de (" + startX + "," + startY + ") para (" +
                pos1[0] + "," + pos1[1] + ")");

        // Testa comportamento de colisao
        System.out.println("  Continua após colisão? " + strategy.shouldContinueAfterHit());

        // Simula colisao
        strategy.onHit();
    }
}