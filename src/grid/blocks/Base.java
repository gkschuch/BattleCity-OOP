package grid.blocks;

public class Base extends Block {

	// contrutor

	public Base(int row, int col) {
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
	public boolean isBase() {
		return true;
	}
}
