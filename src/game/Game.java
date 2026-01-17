package game;

import characters.TankPlayer;
import grid.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ui.Hud;
import utils.Direction;

public class Game {
	// atributos
	private final InputHandler input = new InputHandler();
	private final EnemyManager enemyManager = new EnemyManager();
	private final GameStateManager stateManager = new GameStateManager();
	private final List<Shot> shots = Collections.synchronizedList(new ArrayList<>());

	// construtor

	// métodos

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public void run() {
		GameSetup setup = new GameSetup(new Scanner(System.in));
		String playerName = setup.askPlayerName();
		Grid grid = new Grid(setup.askMapChoice());
		int difficulty = setup.askDifficulty();

		TankPlayer player = new TankPlayer(playerName, 1, 1, 20, 1.0);
		player.start();

		enemyManager.spawnEnemies(difficulty, player, grid);
		PowerUpSpawner spawner = new PowerUpSpawner(grid, enemyManager.getEnemies());
		Thread spawnerThread = new Thread(spawner);
		spawnerThread.start();

		input.start();
		long tick = 0;
		while (input.getRunning()) {
			tick++;
			handleInput(player, grid);

			updateWorld(grid, player, tick);

			clearScreen();
			ConsoleRenderer.render(new Hud(), grid, player, enemyManager.getEnemies(), shots);

			if (stateManager.isGameOver(grid, player, enemyManager.countAlive()))
				break;

			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		cleanup(player, spawner);
		stateManager.finalizeGame(player);
	}

	private void handleInput(TankPlayer player, Grid grid) {
		char cmd = input.pollCommand();

		if (cmd == 'q') {
			System.out.println("\nSaindo...");
			return;
		}

		switch (cmd) {
			case 'w' -> {
				MovementSystem.tryMovePlayer(grid, player, Direction.UP, enemyManager.getEnemies());
			}
			case 's' -> {
				MovementSystem.tryMovePlayer(grid, player, Direction.DOWN, enemyManager.getEnemies());
			}
			case 'a' -> {
				MovementSystem.tryMovePlayer(grid, player, Direction.LEFT, enemyManager.getEnemies());
			}
			case 'd' -> {
				MovementSystem.tryMovePlayer(grid, player, Direction.RIGHT, enemyManager.getEnemies());
			}
			case 'f' -> {
				ShotSystem.playerShoot(shots, grid, player);
			}
			default -> {
			}
		}
	}

	private void updateWorld(Grid grid, TankPlayer player, long tick) {
		enemyManager.updateEnemies(grid, player, tick);
		ShotSystem.enemiesRandomShoot(shots, grid, enemyManager.getEnemies(), 0.04);
		CollisionSystem.handleShotsVsTanks(shots, enemyManager.getEnemies(), player);
		ShotSystem.cleanupShots(shots);
	}

	private void cleanup(TankPlayer player, PowerUpSpawner spawner) {
		input.stop();
		ShotSystem.stopAllShots(shots);
		spawner.stop();
		player.stop();
		enemyManager.stopAll();
	}
}