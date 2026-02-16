package ui;

import characters.TankPlayer;
import grid.Grid;
import ui.exceptions.MissingRenderContextException;

public class Hud {

	// métodos

	public void clear() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void draw(TankPlayer p, Grid g) {
		if (p == null)
			throw new MissingRenderContextException("TankPlayer no HUD");
		if (g == null)
			throw new MissingRenderContextException("Grid no HUD");
		System.out.println("Player: " + p.getPlayerName() + " | Vidas: " + p.getLives() + " | Score: " + p.getScore()
				+ " | Arma: " + p.getGunLevel());

		if (g.isBaseDestroyed()) {
			System.out.println("Base: DESTRUIDA");
		} else {
			System.out.println("Base: OK");
		}

		System.out.println("Controles: W/A/S/D mover | F atirar | Q sair");
		System.out.println();
	}
}
