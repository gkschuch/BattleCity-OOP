package characters.enemy;

public class ArmoredTank extends EnemyTank {
    public ArmoredTank() {
        super(4, 2, 300);
    }

    @Override
    public void updateIA() {
        // Logica de movimento
    }

    @Override
    public void shoot() {
        // Na versao final cria o objeto projetil
        System.out.println("Armored Tank is shooting");
    }

}
