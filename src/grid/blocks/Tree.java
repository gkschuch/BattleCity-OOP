package grid.blocks;

import java.awt.image.BufferedImage;

import utils.SpriteManager;

public class Tree extends Block {

	// contrutor

	public Tree(int row, int col) {
		super(row, col);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		return new java.awt.Color(34, 139, 34); // Verde floresta
	}

	@Override
	public BufferedImage getImage() {
		return SpriteManager.getSprite(922, 365, 184, 205);
	}

	@Override
	public boolean isWalkable() {
		return true;
	}

	@Override
	public boolean isProjectilePassThrough() {
		return true;
	}
}
