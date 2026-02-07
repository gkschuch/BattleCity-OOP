package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import characters.powerups.PowerUp;
import game.persistence.JsonSaveManager;
import grid.Grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ui.GameFrame;
import ui.GamePanel;
import ui.Hud;

public class Game {
	private final InputController input = new InputController();
	private final EnemyManager enemyManager = new EnemyManager();
	private final GameStateManager stateManager = new GameStateManager();
	private final List<Shot> shots = Collections.synchronizedList(new ArrayList<>());

	private TankPlayer player;
	private Grid grid;
	private int difficulty;
	private String mapPath;

	public Game() {
	}

	public void run() {
		GameSetup setup = new GameSetup();
		String playerName = setup.askPlayerName();
		this.mapPath = setup.askMapChoice();
		this.grid = new Grid(mapPath);
		this.difficulty = setup.askDifficulty();

		this.player = new TankPlayer(playerName, 1, 1, 20, 1.0);
		this.player.setGrid(grid);
		this.player.start();

		enemyManager.spawnEnemies(difficulty, player, grid);
		PowerUpSpawner spawner = new PowerUpSpawner(grid, enemyManager.getEnemies(), input);
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

			input.processInput(player, grid, enemyManager.getEnemies(), shots, this);

			if (!input.isPaused()) {
				updateWorld(grid, player, tick);
			}

			panel.repaint();

			// ConsoleRenderer.render(new Hud(), grid, player, enemyManager.getEnemies(),
			// shots);

			if (stateManager.isGameOver(grid, player, enemyManager.countAlive()))
				break;

			try {
				Thread.sleep(80);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				break;
			}
		}

		// 6. Finalização
		cleanup(player, spawner, frame);
		stateManager.finalizeGame(player);
	}

	public void save() {
		if (player == null || grid == null) {
			System.out.println("Erro: Jogo ainda não inicializado para salvar.");
			return;
		}

		List<EnemyTank> currentEnemies = enemyManager.getEnemies();
		List<PowerUp> currentPowerUps = grid.getActivePowerUps();

		JsonSaveManager.saveGame(
				this.player,
				currentEnemies,
				currentPowerUps,
				this.grid,
				this.difficulty,
				this.mapPath);

		System.out.println("Progresso guardado com sucesso no arquivo JSON!");
	}

	private void updateWorld(Grid grid, TankPlayer player, long tick) {
		enemyManager.updateEnemies(grid, player, tick);
		ShotSystem.enemiesRandomShoot(shots, grid, enemyManager.getEnemies(), 0.04, input);
		CollisionSystem.handleShotsVsTanks(shots, enemyManager.getEnemies(), player);
		ShotSystem.cleanupShots(shots);
	}

	private void cleanup(TankPlayer player, PowerUpSpawner spawner, GameFrame gameFrame) {
		input.stop();
		ShotSystem.stopAllShots(shots);
		spawner.stop();
		player.stop();
		enemyManager.stopAll();

		if (gameFrame != null)
			gameFrame.dispose();
	}
}