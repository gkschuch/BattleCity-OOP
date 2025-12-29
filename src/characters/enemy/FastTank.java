package characters.enemy;

public class FastTank extends EnemyTank {
    public FastTank() {
        super(2, 10, 150);
    }

    @Override
    public void updateIA() {
        // Logica de movimento
    }

    @Override
    public void shoot() {
        // Na versao final cria o objeto projetil
        System.out.println("Fast Enemy Tank is shooting.");
    }

}
