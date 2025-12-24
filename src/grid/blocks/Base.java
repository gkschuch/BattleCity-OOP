package grid.blocks;

public class Base extends Block {

    // construtor: cria a base em tal posição posição 
    public Base(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable() {     // tanque não pode passar por cima da base
        return false;
    }

    @Override
    public boolean isProjectilePassThrough() {  // tiro não atravessa a base
        return false;
    }
}
