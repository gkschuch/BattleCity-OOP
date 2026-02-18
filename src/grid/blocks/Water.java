package grid.blocks;

import utils.SpriteManager;
import java.awt.image.BufferedImage;

public class Water extends Block {
	private BufferedImage myImage;
	// contrutor

	public Water(int row, int col) {
		super(row, col);

		this.myImage = SpriteManager.getSprite(1163, 361, 205, 212);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		return java.awt.Color.BLUE; // Azul
	}

	@Override
	public BufferedImage getImage() {
		return this.myImage;
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
