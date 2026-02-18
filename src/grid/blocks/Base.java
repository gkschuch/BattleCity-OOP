package grid.blocks;

import java.awt.image.BufferedImage;

import utils.SpriteManager;

public class Base extends Block {
	private BufferedImage myImageDestroyed;
	private BufferedImage myImage;
	// contrutor

	public Base(int row, int col) {
		super(row, col);

		this.myImageDestroyed = SpriteManager.getSprite(320, 32, 16, 16);
		this.myImage = SpriteManager.getSprite(1433, 355, 182, 233);
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
	public BufferedImage getImage() {
		if (this.isDestroyed())
			return this.myImageDestroyed;
		return this.myImage;
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
