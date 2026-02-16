package grid.blocks;

public class Base extends Block {

	// contrutor

	public Base(int row, int col) {
		super(row, col);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		if (this.isDestroyed()) {
			// Cor de cinza escuro/preto se a base foi explodida
			return java.awt.Color.BLACK;
		}
		// Cor Ouro (RGB: 255, 215, 0) para a base ativa
		return new java.awt.Color(255, 215, 0);
	}

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
