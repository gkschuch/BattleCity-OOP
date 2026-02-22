package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import java.util.List;

import utils.FontManager;

import characters.TankPlayer;
import grid.Grid;
import ui.exceptions.MissingRenderContextException;
import characters.powerups.PowerUp;

public class Hud {

	public void drawRight(Graphics g, TankPlayer p, Grid grid, int screenWidth, int screenHeight, int hudX) {
		if (p == null)
			throw new MissingRenderContextException("TankPlayer no HUD");
		if (grid == null)
			throw new MissingRenderContextException("Grid no HUD");
		if (g == null)
			throw new MissingRenderContextException("Graphics no HUD");

		int panelWidth = 320;
		int panelX = hudX;

		if (panelX + panelWidth > screenWidth - 10) {
			panelX = screenWidth - panelWidth - 10;
		}
		if (panelX < 10)
			panelX = 10;

		int panelY = 60;
		int panelHeight = screenHeight - 120;

		g.setColor(new Color(0, 0, 0, 160));
		g.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 18, 18);

		g.setColor(Color.WHITE);
		g.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 18, 18);

		Font titleFont = FontManager.getFont(28f);
		Font textFont = FontManager.getFont(20f);

		g.setFont(titleFont);
		FontMetrics fmTitle = g.getFontMetrics();

		g.setFont(textFont);
		FontMetrics fmText = g.getFontMetrics();

		int x = panelX + 16;
		int y = panelY + 70;
		int line = fmText.getHeight() + 6;

		g.setColor(Color.YELLOW);
		g.drawString("INFORMAÇÕES: ", x, y);
		y += line;
		g.setColor(Color.WHITE);

		g.drawString("Jogador: " + p.getPlayerName(), x, y);
		y += line;
		g.drawString("Vidas:   " + p.getLives(), x, y);
		y += line;
		g.drawString("Score:   " + p.getScore(), x, y);
		y += line;
		g.drawString("Arma:    " + p.getGunLevel(), x, y);
		y += line;
		String baseStatus = grid.isBaseDestroyed() ? "DESTRUÍDA" : "SEGURA";
		g.setColor(Color.WHITE);
		g.drawString("Base: ", x, y);
		g.setColor(grid.isBaseDestroyed() ? Color.RED : Color.GREEN);
		g.drawString(baseStatus, x + 70, y);
		y += line;

		g.setColor(Color.YELLOW);
		g.drawString("POWERUPS: ", x, y);
		y += 10;

		List<PowerUp> activePowerUps = grid.getActivePowerUps();

		if (activePowerUps.isEmpty()) {
			g.setColor(Color.GRAY);
			g.drawString("Nenhum no mapa", x, y + line);
		} else {
			int puX = x;
			int puY = y + 5;
			int iconSize = 35;

			for (PowerUp pu : activePowerUps) {
				if (pu != null && pu.getImage() != null) {
					g.drawImage(pu.getImage(), puX, puY, iconSize, iconSize, null);

					puX += iconSize + 10;
					if (puX > panelX + panelWidth - 50) {
						puX = x;
						puY += iconSize + 10;
					}
				}
			}
		}

		y += line + 230;
		g.setColor(Color.YELLOW);
		g.drawString("CONTROLES:", x, y);
		y += line;
		g.setColor(Color.WHITE);
		g.drawString("W/A/S/D  : mover", x, y);
		y += line;
		g.drawString("SPACE    : atirar", x, y);
		y += line;
		g.drawString("P        : pausar", x, y);
		y += line;
		g.drawString("Q        : sair", x, y);
	}
}