package grid.blocks;

import java.awt.image.BufferedImage;
import utils.SpriteManager;

public class Brick extends Block {
	private BufferedImage myImage;
	// contrutor

	public Brick(int row, int col) {
		super(row, col);
		this.myImage = SpriteManager.getSprite(393, 356, 224, 220);
	}

	// métodos

	@Override
	public char getIcon() {
		return 'B';
	}

	@Override
	public java.awt.Color getColor() {
		return new java.awt.Color(165, 42, 42);
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
		return false;
	}
}
