package grid.blocks;

public class Water extends Block {

	// contrutor

	public Water(int row, int col) {
		super(row, col);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		return java.awt.Color.BLUE; // Azul
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public boolean isProjectilePassThrough() {
		return true;
	}
}
