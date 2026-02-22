package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackgroundImagePanel extends JPanel {
	private final String        imageFilePath;
	private       BufferedImage backgroundImage;

	public BackgroundImagePanel(String imageFilePath) {
		this.imageFilePath = imageFilePath;
		loadBackgroundImage();
		setLayout(new GridBagLayout());
	}

	private void loadBackgroundImage() {
		try {
			backgroundImage = ImageIO.read(new File(imageFilePath));
		} catch ( IOException e ) {
			backgroundImage = null;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if ( backgroundImage == null )
			return;

		Graphics2D g2 = ( Graphics2D ) g.create();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

		g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		g2.dispose();
	}
}