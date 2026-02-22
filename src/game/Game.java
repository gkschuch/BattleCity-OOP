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

	public Game() {
	}

	public void startGame(boolean isNewGame, String playerName, String mapPath, int difficulty) {
		if (isNewGame) {
			this.mapPath = mapPath;
			this.grid = new Grid(mapPath);
			this.difficulty = difficulty;

			this.player = new TankPlayer(playerName, 1, 11, 4, 1.0);
			this.player.setGrid(grid);
			this.player.start();
			enemyManager.spawnEnemies(difficulty, player, grid);
		} else {
			load();
		}
		this.powerUpSpawner = new PowerUpSpawner(grid, enemyManager.getEnemies(), input);
		Thread spawnerThread = new Thread(powerUpSpawner);
		spawnerThread.start();

		GameFrame frame = new GameFrame(input);
		this.panel = new GamePanel(grid, player, enemyManager.getEnemies(), shots);
		frame.add(panel);
		frame.setVisible(true);

		long tick = 0;
		try {
			while (input.isRunning()) {
				tick++;

				input.processInput(player, grid, enemyManager.getEnemies(), shots, this);

				if (!input.isPaused()) {
					updateWorld(grid, player, tick);
				}

				panel.setPaused(input.isPaused());

				panel.repaint();

				stateManager.checkGameState(grid, player, enemyManager.countAlive());
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
		} finally {
			cleanup(player, powerUpSpawner, frame);
			stateManager.finalizeGame(player);
		}
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
					this.mapPath);

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