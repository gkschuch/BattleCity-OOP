package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;

import utils.FontManager;
import utils.SpriteManager;

import characters.TankPlayer;
import grid.Grid;
import ui.exceptions.MissingRenderContextException;
import characters.powerups.PowerUpType;

public class Hud {

	private final Map<PowerUpType, BufferedImage> powerUpIcons = new EnumMap<>(PowerUpType.class);

	public Hud() {
		loadPowerUpIcons();
	}

	private void loadPowerUpIcons() {
		powerUpIcons.put(PowerUpType.HELMET, SpriteManager.getSprite(864, 83, 166, 159));
		powerUpIcons.put(PowerUpType.CLOCK, SpriteManager.getSprite(1316, 74, 160, 177));
		powerUpIcons.put(PowerUpType.SHOVEL, SpriteManager.getSprite(1087, 66, 172, 181));
		powerUpIcons.put(PowerUpType.STAR, SpriteManager.getSprite(643, 64, 177, 180));
		powerUpIcons.put(PowerUpType.BOMB, SpriteManager.getSprite(1537, 73, 164, 174));
		powerUpIcons.put(PowerUpType.LIFE, SpriteManager.getSprite(1755, 86, 158, 154));
	}

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

		Font textFont = FontManager.getFont(20f);
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
		g.drawString("Base: ", x, y);
		g.setColor(grid.isBaseDestroyed() ? Color.RED : Color.GREEN);
		g.drawString(baseStatus, x + 70, y);
		y += line;

		g.setColor(Color.YELLOW);
		g.drawString("GUIA DE POWER-UPS:", x, y);
		y += 15;

		int iconSize = 25;
		g.setFont(FontManager.getFont(14f));

		for (PowerUpType type : PowerUpType.values()) {
			BufferedImage img = powerUpIcons.get(type);
			if (img != null) {
				g.drawImage(img, x, y - 18, iconSize, iconSize, null);

				String description = switch (type) {
					case STAR -> "Melhora a arma";
					case HELMET -> "Invencibilidade";
					case SHOVEL -> "Protege a base";
					case CLOCK -> "Congela inimigos";
					case BOMB -> "Limpa o mapa";
					case LIFE -> "Vida extra";
				};

				g.setColor(Color.WHITE);
				g.drawString(": " + description, x + iconSize + 5, y);
				y += line;
			}
		}

		g.setFont(textFont);
		y += 10;
		g.setColor(Color.YELLOW);
		g.drawString("CONTROLES:", x, y);
		y += line;

		g.setColor(Color.WHITE);
		int columnOffset = 110;

		g.drawString("W/A/S/D", x, y);
		g.drawString(": mover", x + columnOffset, y);
		y += line;
		g.drawString("SPACE", x, y);
		g.drawString(": atirar", x + columnOffset, y);
		y += line;
		g.drawString("P", x, y);
		g.drawString(": pausar", x + columnOffset, y);
	}
}