package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import characters.powerups.PowerUp;
import characters.powerups.PowerUpFactory;
import game.exceptions.GameTerminationException;
import game.persistence.EnemySaveData;
import game.persistence.GameSaveData;
import game.persistence.JsonSaveManager;
import game.persistence.PowerUpSaveData;
import grid.Grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import ui.GameFrame;
import ui.GamePanel;

public class Game {
	private final InputController input = new InputController();
	private final EnemyManager enemyManager = new EnemyManager();
	private final GameStateManager stateManager = new GameStateManager();
	private final List<Shot> shots = Collections.synchronizedList(new ArrayList<>());
	private PowerUpSpawner powerUpSpawner;

	private TankPlayer player;
	private Grid grid;
	private int difficulty;
	private String mapPath;
	private GamePanel panel;
	private long elapsedTime = 0;

	private int targetEnemyCount;
	private int currentWave = 1;

	public Game() {
	}

	public void startGame(boolean isNewGame, String playerName, String mapPath, int difficulty) {
		if (isNewGame) {
			this.elapsedTime = 0;
			this.mapPath = mapPath;
			this.grid = new Grid(mapPath);
			this.difficulty = difficulty;

			this.player = new TankPlayer(playerName, 1, 11, 4, 1.0);
			this.player.setGrid(grid);
			this.player.start();
			enemyManager.spawnEnemies(difficulty, player, grid);
			this.targetEnemyCount = enemyManager.getEnemies().size();
		} else {
			load();
			this.targetEnemyCount = enemyManager.getEnemies().size();
		}
		this.powerUpSpawner = new PowerUpSpawner(grid, enemyManager.getEnemies(), input);
		Thread spawnerThread = new Thread(powerUpSpawner);
		spawnerThread.start();

		GameFrame frame = new GameFrame(input);
		this.panel = new GamePanel(grid, player, enemyManager.getEnemies(), shots);
		frame.add(panel);
		frame.setVisible(true);

		long limitTimeMs = 60000;
		long lastTickTime = System.currentTimeMillis();
		long tick = 0;

		try {
			while (input.isRunning()) {
				long currentTime = System.currentTimeMillis();
				long deltaTime = currentTime - lastTickTime;
				lastTickTime = currentTime;

				tick++;
				input.processInput(player, grid, enemyManager.getEnemies(), shots, this);

				if (!input.isPaused()) {
					this.elapsedTime += deltaTime;
					updateWorld(grid, player, tick);
					if (enemyManager.countAlive() == 0) {
						advanceToNextWave();
					}
				}

				long timeRemaining = limitTimeMs - this.elapsedTime;
				panel.setTimeRemainingMs(Math.max(0, timeRemaining));
				panel.setPaused(input.isPaused());
				panel.repaint();

				stateManager.checkGameState(grid, player, enemyManager.countAlive(), this.elapsedTime);
				try {
					Thread.sleep(160);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		} catch (GameTerminationException e) {
			System.out.println(e.getMessage());
			input.stop();
			javax.swing.SwingUtilities.invokeLater(() -> {
				ui.MainMenu.showEndScreen(
						e.isVictory() ? "VITÓRIA!" : "GAME OVER",
						e.getMessage(),
						player);
			});

		} finally {
			cleanup(player, powerUpSpawner, frame);

			stateManager.finalizeGame(player);
		}
	}

	private void advanceToNextWave() throws game.exceptions.GameTerminationException {
		String[] options = { "Continuar", "Sair" };

		int choice = JOptionPane.showOptionDialog(panel,
				"Wave " + currentWave + " concluída!\nO que deseja fazer?",
				"Vitória da Wave!",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[0]);

		if (choice != 0) {
			throw new game.exceptions.AllEnemiesDestroyedException();
		}

		this.currentWave++;

		this.targetEnemyCount = (int) Math.ceil(this.targetEnemyCount * 1.3);
		this.elapsedTime = 0;

		String[] availableMaps = {
				"src/grid/models/model_classic.txt",
				"src/grid/models/model_maze.txt",
				"src/grid/models/model_strength.txt"
		};

		this.mapPath = availableMaps[new java.util.Random().nextInt(availableMaps.length)];
		this.grid = new Grid(this.mapPath);

		this.player.setX(1);
		this.player.setY(11);
		this.player.setGrid(this.grid);

		ShotSystem.stopAllShots(shots);

		this.powerUpSpawner.stop();
		this.powerUpSpawner = new PowerUpSpawner(this.grid, enemyManager.getEnemies(), input);
		new Thread(this.powerUpSpawner).start();

		enemyManager.spawnWave(this.difficulty, player, grid, this.targetEnemyCount);

		this.panel.updateReferences(this.grid, this.player, enemyManager.getEnemies());
		this.panel.repaint();
	}

	public void save() {
		if (player == null || grid == null) {
			System.out.println("Erro: Jogo ainda não inicializado para salvar.");
			return;
		}

		List<EnemyTank> currentEnemies = enemyManager.getEnemies();
		List<PowerUp> currentPowerUps = grid.getActivePowerUps();
		try {
			JsonSaveManager.saveGame(
					this.player,
					currentEnemies,
					currentPowerUps,
					this.grid,
					this.difficulty,
					this.mapPath,
					this.elapsedTime);

			JOptionPane.showMessageDialog(panel, "Progresso guardado com sucesso!", "Save Game",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "Falha ao guardar o jogo:\n" + e.getMessage(), "Erro de Save",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void load() {
		GameSaveData data = JsonSaveManager.loadGame();

		try {
			data = JsonSaveManager.loadGame();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(panel, "Não foi possível carregar o jogo:\n" + e.getMessage(), "Erro de Load",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (data == null) {
			JOptionPane.showMessageDialog(panel, "Nenhum jogo salvo encontrado.", "Load Game",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (this.player != null) {
			this.player.stop();
		}
		if (this.powerUpSpawner != null) {
			this.powerUpSpawner.stop();
		}
		enemyManager.clearEnemies();
		ShotSystem.stopAllShots(shots);
		shots.clear();

		this.mapPath = data.mapPath;
		this.difficulty = data.difficulty;
		this.elapsedTime = data.elapsedTime;

		this.grid = new Grid(mapPath);
		this.grid.applySavedLayout(data.gridLayout);

		this.player = new TankPlayer(
				data.player.name,
				data.player.x,
				data.player.y,
				data.player.lives,
				data.player.speed);
		this.player.setScore(data.player.score);
		this.player.setGunLevel(data.player.gunLevel);
		this.player.setDirection(data.player.direction);
		this.player.setGrid(grid);
		this.player.start();

		for (EnemySaveData eData : data.enemies)
			enemyManager.addSavedEnemy(eData, player, grid);

		for (PowerUpSaveData pData : data.powerUps) {
			PowerUp powerUp = PowerUpFactory.createByType(
					pData.type,
					pData.row,
					pData.col,
					this.grid,
					this.enemyManager.getEnemies());
			grid.addPowerUp(powerUp);
		}

		if (this.panel != null) {
			this.panel.updateReferences(this.grid, this.player, enemyManager.getEnemies());
			this.panel.repaint();
		}
		System.out.println("Jogo carregado com sucesso! A retomar...");
	}

	private void updateWorld(Grid grid, TankPlayer player, long tick) {
		enemyManager.updateEnemies(grid, player, tick);
		ShotSystem.enemiesRandomShoot(shots, grid, enemyManager.getEnemies(), 0.04, input, this.difficulty);
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