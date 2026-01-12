package ui;

import characters.TankPlayer;
import grid.Grid;

public class Hud {

	// métodos

	public void clear() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void draw(TankPlayer p, Grid g) {
		System.out.println("Player: " + p.getPlayerName() + " | Vidas: " + p.getLives() + " | Score: " + p.getScore() + " | Arma: " + p.getGunLevel());

		if ( g.isBaseDestroyed() ) {
			System.out.println("Base: DESTRUIDA");
		} else {
			System.out.println("Base: OK");
		}

		System.out.println("Controles: W/A/S/D mover | F atirar | Q sair");
		System.out.println();
	}
}
