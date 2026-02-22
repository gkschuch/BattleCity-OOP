package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import characters.TankPlayer;
import grid.Grid;
import ui.exceptions.MissingRenderContextException;

public class Hud {

	public void draw(Graphics g, TankPlayer p, Grid grid, int screenWidth, int screenHeight) {
		if (p == null)
			throw new MissingRenderContextException("TankPlayer no HUD");
		if (grid == null)
			throw new MissingRenderContextException("Grid no HUD");
		if (g == null)
			throw new MissingRenderContextException("Graphics no HUD");

		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, screenWidth, 30);

		g.setFont(utils.FontManager.getFont(20f));
		FontMetrics fm = g.getFontMetrics();

		String stats = String.format("Nome do Jogador: %s | Vidas: %d | Score: %d | Arma: %d   |   ",
				p.getPlayerName(), p.getLives(), p.getScore(), p.getGunLevel());
		String baseStatus = grid.isBaseDestroyed() ? "Base: DESTRUÍDA" : "Base: SEGURA";

		int statsWidth = fm.stringWidth(stats);
		int baseWidth = fm.stringWidth(baseStatus);
		int totalTopWidth = statsWidth + baseWidth;

		int topStartX = (screenWidth - totalTopWidth) / 2;

		g.setColor(Color.WHITE);
		g.drawString(stats, topStartX, 20);

		if (grid.isBaseDestroyed()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.GREEN);
		}
		g.drawString(baseStatus, topStartX + statsWidth, 20);

		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, screenHeight - 30, screenWidth, 30);

		String bottomText = "W/A/S/D: Mover | SPACE: Atirar | P: Pausar";
		int bottomWidth = fm.stringWidth(bottomText);

		int bottomStartX = (screenWidth - bottomWidth) / 2;

		g.setColor(Color.LIGHT_GRAY);
		g.drawString(bottomText, bottomStartX, screenHeight - 10);
	}
}