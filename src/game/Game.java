package game;

import characters.TankPlayer;
import grid.Grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import ui.GameFrame;
import ui.GamePanel;
import ui.Hud;

public class Game {
	// atributos
	private final InputController input = new InputController();
	private final EnemyManager enemyManager = new EnemyManager();
	private final GameStateManager stateManager = new GameStateManager();
	private final List<Shot> shots = Collections.synchronizedList(new ArrayList<>());

	// construtor
	public Game() {
	}

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

		GameFrame frame = new GameFrame(input);
		GamePanel panel = new GamePanel(grid, player, enemyManager.getEnemies(), shots);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		long tick = 0;
		while (input.isRunning()) {
			tick++;
			input.processInput(player, grid, enemyManager.getEnemies(), shots);

			updateWorld(grid, player, tick);

			panel.repaint();

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