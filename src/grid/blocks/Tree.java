package grid.blocks;

public class Tree extends Block {

    // contrutor

    public Tree(int row, int col) {
        super(row, col);
    }

    // métodos
    
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean isProjectilePassThrough() {
        return true;
    }
}
