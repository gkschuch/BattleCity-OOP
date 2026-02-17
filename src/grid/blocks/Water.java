package grid.blocks;

import utils.SpriteManager;
import java.awt.image.BufferedImage;

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
	public BufferedImage getImage() {
		return SpriteManager.getSprite(1163, 361, 205, 212);
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
