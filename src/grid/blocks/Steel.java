package grid.blocks;

public class Steel extends Block {

    // contrutor

    public Steel(int row, int col) {
        super(row, col);
    }

    // métodos

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean isProjectilePassThrough() {
        return false;
    }

    @Override
    public void takeDamage(int damage) {
    }
}
