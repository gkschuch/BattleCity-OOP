package game;

import characters.TankPlayer;
import characters.enemy.ArmedTank;
import characters.enemy.ArmoredTank;
import characters.enemy.EnemyTank;
import characters.enemy.FastTank;
import characters.enemy.NormalTank;
import grid.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ranking.RankingManager;
import ui.Hud;
import utils.Direction_enum;

public class Game {
	// atributos

	private final InputHandler input;

	// construtor

	public Game() {
		this.input = new InputHandler();
	}

	// métodos

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	private void spawnEnemiesByDifficulty(int difficulty, List<EnemyTank> enemies, TankPlayer player) {
		enemies.clear();

		switch ( difficulty ) {
			case 1 -> {
				enemies.add(new NormalTank(10, 2, player));
				enemies.add(new FastTank(2, 2, player));
			}
			case 2 -> {
				enemies.add(new NormalTank(10, 2, player));
				enemies.add(new FastTank(2, 2, player));
				enemies.add(new ArmedTank(10, 14, player));
			}
			default -> {
				enemies.add(new NormalTank(10, 2, player));
				enemies.add(new FastTank(2, 2, player));
				enemies.add(new ArmedTank(10, 14, player));
				enemies.add(new ArmoredTank(2, 14, player));
			}
		}

		// começa a thread de cada inimigo assim que eles nascem
		for ( EnemyTank e : enemies ) {
			if ( e != null ) {
				e.start();
			}
		}
	}

	public void run() {
		Scanner   sc    = new Scanner(System.in);
		GameSetup setup = new GameSetup(sc);

		String playerName = setup.askPlayerName();
		int    difficulty = setup.askDifficulty();

		Grid grid = new Grid();
		Hud  hud  = new Hud();

		RankingManager rankingManager = new RankingManager();

		TankPlayer player = new TankPlayer(playerName, 1, 1, 20, 1.0, rankingManager);
		player.setX(1);
		player.setY(1);
		player.setDirection(Direction_enum.UP);

		// inicia a thread do jogador
		player.start();

		List<EnemyTank> enemies = new ArrayList<>();
		spawnEnemiesByDifficulty(difficulty, enemies, player);

		List<Shot> shots = Collections.synchronizedList(new ArrayList<>());

		input.start();

		long    tick  = 0;
		boolean ended = false;


		while ( input.getRunning() && !ended ) {
			tick++;

			char cmd = input.pollCommand();

			if ( cmd == 'q' ) {
				System.out.println("\nSaindo...");
				break;
			}

			switch ( cmd ) {
				case 'w' -> {

					MovementSystem.tryMovePlayer(grid, player, Direction_enum.UP, enemies);

				}
				case 's' -> {
					MovementSystem.tryMovePlayer(grid, player, Direction_enum.DOWN, enemies);

				}
				case 'a' -> {
					MovementSystem.tryMovePlayer(grid, player, Direction_enum.LEFT, enemies);
				}
				case 'd' -> {
					MovementSystem.tryMovePlayer(grid, player, Direction_enum.RIGHT, enemies);
				}
				case 'f' -> {
					ShotSystem.playerShoot(shots, grid, player);
				}
				default -> {
				}
			}


			if ( tick % 4 == 0 ) {
				for ( int i = 0; i < enemies.size(); i++ ) {
					EnemyTank e = enemies.get(i);

					if ( e == null || e.isDestroyed() )
						continue;

					MovementSystem.stepEnemy(grid, e, player, enemies);
				}
			}

			ShotSystem.enemiesRandomShoot(shots, grid, enemies, 0.04);

			CollisionSystem.handleShotsVsTanks(shots, enemies, player);

			ShotSystem.cleanupShots(shots);

			clearScreen();
			ConsoleRenderer.render(hud, grid, player, enemies, shots);


			if ( grid.isBaseDestroyed() ) {
				System.out.println("\nGAME OVER: a base foi destruída.");
				ended = true;
			}

			if ( player.getLives() <= 0 ) {
				System.out.println("\nGAME OVER: você ficou sem vidas.");
				ended = true;
			}

			if ( MovementSystem.countAlive(enemies) == 0 ) {
				System.out.println("\nVOCE VENCEU: todos os inimigos foram destruídos.");
				ended = true;
			}

			try {
				Thread.sleep(80);
			} catch ( InterruptedException ex ) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		input.stop();
		ShotSystem.stopAllShots(shots);

		player.stop();
		for ( EnemyTank e : enemies ) {
			if ( e != null ) {
				e.stop();
			}
		}

		rankingManager.addEntry(player.getPlayerName(), player.getScore());
		rankingManager.printRanking();
	}
}