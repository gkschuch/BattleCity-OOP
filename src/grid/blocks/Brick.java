package grid.blocks;

import java.awt.image.BufferedImage;
import utils.SpriteManager;

public class Brick extends Block {

	// contrutor

	public Brick(int row, int col) {
		super(row, col);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		return new java.awt.Color(165, 42, 42);
	}

	@Override
	public BufferedImage getImage() {
		return SpriteManager.getSprite(393, 356, 224, 220);
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
