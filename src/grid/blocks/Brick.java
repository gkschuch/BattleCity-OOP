package grid.blocks;

public class Brick extends Block {

	// contrutor

	public Brick(int row, int col) {
		super(row, col);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		return new java.awt.Color(165, 42, 42); // Marrom/Tijolo
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public boolean isProjectilePassThrough() {
		return false;
	}
}
