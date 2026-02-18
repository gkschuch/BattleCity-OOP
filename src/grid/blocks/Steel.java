package grid.blocks;

import java.awt.image.BufferedImage;

import utils.SpriteManager;

public class Steel extends Block {
	private BufferedImage myImage;
	// contrutor

	public Steel(int row, int col) {
		super(row, col);

		this.myImage = SpriteManager.getSprite(660, 360, 207, 214);
	}

	// métodos

	@Override
	public java.awt.Color getColor() {
		return java.awt.Color.LIGHT_GRAY; // Cinza claro
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

	@Override
	public void takeDamage(int damage) {
	}
}
