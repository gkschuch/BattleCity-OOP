package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import grid.Grid;
import grid.blocks.Block;
import java.util.List;

import ui.Hud;

public class ConsoleRenderer {
	// construtor

	private ConsoleRenderer() {
	}

	// métodos

	public static void render(Hud hud, Grid grid, TankPlayer player, List<EnemyTank> enemies, List<Shot> shots) {
		hud.clear();
		hud.draw(player, grid);

		System.out.println("Legenda: P=player E=inimigo *=tiro X=base x=base destruida T=arvore ~=agua #=parede .=vazio");
		drawWorld(grid, player, enemies, shots);
	}

	public static void drawWorld(Grid grid, TankPlayer player, List<EnemyTank> enemies, List<Shot> shots) {
		int pr = (int) player.getY();
		int pc = (int) player.getX();

		for (int r = 0; r < grid.getRows(); r++) {
			StringBuilder sb = new StringBuilder();

			for (int c = 0; c < grid.getCols(); c++) {
				char ch;

				if (r == pr && c == pc)
					ch = 'P';
				else if (MovementSystem.isEnemyAt(enemies, r, c))
					ch = 'E';
				else if (isShotAt(shots, r, c))
					ch = '*';
				else
					ch = charFor(grid.getBlock(r, c));

				sb.append(ch);
			}

			System.out.println(sb.toString());
		}
	}

	private static boolean isShotAt(List<Shot> shots, int row, int col) {
		synchronized (shots) {
			for (int i = 0; i < shots.size(); i++) {
				Shot s = shots.get(i);

				if (s == null || s.p == null)
					continue;

				if (s.p.isActive() && s.p.getY() == row && s.p.getX() == col)
					return true;
			}
		}

		return false;
	}

	private static char charFor(Block b) {
		if (b == null)
			return '.';
		return b.getIcon();
	}
}
