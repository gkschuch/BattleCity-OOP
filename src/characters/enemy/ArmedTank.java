package characters.enemy;

public class ArmedTank extends EnemyTank {
    public ArmedTank() {
        super(2, 5, 200);
    }

    @Override
    public void updateIA() {
        // Logica de movimento
    }

    @Override
    public void shoot() {
        // Na versao final cria o objeto projetil - de mais dano (ou mais de um tiro)
        System.out.println("Armed Enemy Tank is shooting.");
    }
}
