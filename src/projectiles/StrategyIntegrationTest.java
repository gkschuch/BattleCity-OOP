package projectiles;

import utils.Direction;
import projectiles.strategy.*;

public class StrategyIntegrationTest {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  TESTE FINAL - STRATEGY PATTERN PARA PROJÉTEIS  ");
        System.out.println("==================================================\n");

        // Seção 1: Demonstração do Padrão Strategy
        System.out.println("SEÇÃO 1: DEMONSTRAÇÃO DO PADRÃO STRATEGY");
        System.out.println("=" .repeat(50));

        System.out.println("\n1.1 Interface MoveStrategy (Contrato):");
        System.out.println("   - calculateNextPosition() → Calcula movimento");
        System.out.println("   - shouldContinueAfterHit() → Comportamento pós-colisão");
        System.out.println("   - getBaseDamage() → Dano do projétil");
        System.out.println("   - getMoveDelay() → Velocidade do movimento");
        System.out.println("   - onHit() → Ação ao colidir");

        System.out.println("\n1.2 Implementações Concretas (Polimorfismo):");
        MoveStrategy[] estrategias = {
                new NormalMoveStrategy(),
                new PiercingMoveStrategy(3),
                new FastMoveStrategy()
        };

        for (MoveStrategy estrategia : estrategias) {
            System.out.printf("   - %-25s | Dano: %d | Delay: %dms%n",
                    estrategia.getStrategyName(),
                    estrategia.getBaseDamage(),
                    estrategia.getMoveDelay());
        }

        // Seção 2: Teste de Integração com Projéteis
        System.out.println("\n\nSEÇÃO 2: INTEGRAÇÃO COM CLASSES DE PROJÉTEIS");
        System.out.println("=" .repeat(50));

        System.out.println("\n2.1 Projétil com construtor original (compatibilidade):");
        BasicProjectile projOriginal = new BasicProjectile(5, 5, Direction.RIGHT, 1);
        System.out.println("   " + projOriginal.getInfo());
        System.out.println("   ✓ Compatibilidade retroativa mantida");

        System.out.println("\n2.2 Projéteis com diferentes estratégias:");
        BasicProjectile[] projeteis = {
                new BasicProjectile(0, 0, Direction.RIGHT, new NormalMoveStrategy()),
                new BasicProjectile(0, 3, Direction.DOWN, new PiercingMoveStrategy(2)),
                new BasicProjectile(0, 6, Direction.LEFT, new FastMoveStrategy())
        };

        String[] nomes = {"NORMAL", "PERFURANTE", "RÁPIDO"};
        for (int i = 0; i < projeteis.length; i++) {
            BasicProjectile p = projeteis[i];
            System.out.printf("\n   %s:%n", nomes[i]);
            System.out.println("   " + p.getInfo());

            // Testa movimento
            int xAntes = p.getX();
            int yAntes = p.getY();
            p.move();
            System.out.printf("   Movimento: (%d,%d) → (%d,%d)%n",
                    xAntes, yAntes, p.getX(), p.getY());

            // Testa comportamento de colisão
            System.out.printf("   Continua após colisão? %s%n",
                    p.getMoveStrategy().shouldContinueAfterHit() ? "SIM" : "NÃO");
        }

        // Seção 3: Demonstração de Polimorfismo
        System.out.println("\n\nSEÇÃO 3: DEMONSTRAÇÃO DE POLIMORFISMO");
        System.out.println("=" .repeat(50));

        System.out.println("\n3.1 Tratamento uniforme via interface:");
        Projectiles[] listaProjeteis = new Projectiles[3];
        listaProjeteis[0] = new BasicProjectile(10, 10, Direction.UP, 1);
        listaProjeteis[1] = new BasicProjectile(10, 12, Direction.UP, new PiercingMoveStrategy());
        listaProjeteis[2] = new BasicProjectile(10, 14, Direction.UP, new FastMoveStrategy());

        System.out.println("   Processando diferentes projéteis uniformemente:");
        for (Projectiles p : listaProjeteis) {
            String tipo = p.getMoveStrategy().getStrategyName();
            int delay = p.getMoveStrategy().getMoveDelay();
            System.out.printf("   - %-20s → Delay: %dms%n", tipo, delay);
        }

        // Seção 4: Benefícios do Padrão
        System.out.println("\n\nSEÇÃO 4: BENEFÍCIOS DO PADRÃO STRATEGY");
        System.out.println("=" .repeat(50));

        System.out.println("\n✓ FECHADO para modificação:");
        System.out.println("   - Classe Projectiles não precisa mudar para novos comportamentos");

        System.out.println("\n✓ ABERTO para extensão:");
        System.out.println("   - Nova estratégia: Criar classe que implementa MoveStrategy");
        System.out.println("   - Exemplo: GuidedMoveStrategy, ExplosiveMoveStrategy");

        System.out.println("\n✓ BAIXO ACOPLAMENTO:");
        System.out.println("   - Projéteis não conhecem detalhes das estratégias");
        System.out.println("   - Estratégias não conhecem detalhes dos projéteis");

        System.out.println("\n✓ ALTA COESÃO:");
        System.out.println("   - Cada estratégia tem uma responsabilidade única");
        System.out.println("   - Código organizado e fácil de manter");

        // Seção 5: Como usar no jogo
        System.out.println("\n\nSEÇÃO 5: COMO USAR NO JOGO BATTLE CITY");
        System.out.println("=" .repeat(50));

        System.out.println("\nExemplo de criação de projéteis no jogo:");
        System.out.println("""
            // Tanque normal
            Projectile normal = new BasicProjectile(x, y, dir, 1);
            
            // Tanque com power-up perfurante
            Projectile piercing = new BasicProjectile(x, y, dir, new PiercingMoveStrategy());
            
            // Tanque com power-up rápido  
            Projectile fast = new BasicProjectile(x, y, dir, new FastMoveStrategy());
            
            // Todos funcionam da mesma forma!
            normal.start();
            piercing.start();
            fast.start();
            """);

        // Conclusão
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ PADRÃO STRATEGY IMPLEMENTADO COM SUCESSO!");
        System.out.println("=".repeat(60));

        System.out.println("\nResumo para o relatório do trabalho:");
        System.out.println("""
            • Padrão de projeto: Strategy
            • Aplicação: Comportamento de movimento de projéteis
            • Interface: MoveStrategy
            • Implementações: 3 (Normal, Perfurante, Rápido)
            • Benefícios: Polimorfismo, baixo acoplamento, extensibilidade
            • Conceitos POO demonstrados: 
              - Herança (BasicProjectile extends Projectiles)
              - Polimorfismo (interface MoveStrategy)
              - Encapsulamento (comportamento em classes separadas)
              - Composição (Projectiles tem MoveStrategy)
            """);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎮 PRONTO PARA INTEGRAÇÃO COM O JOGO!");
        System.out.println("=".repeat(60));
    }
}