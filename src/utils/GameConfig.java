package utils;

public final class GameConfig {
	private GameConfig() {
	}

	// Configuracao do ranking
	public static final int MAX_ENTRIES = 10;

	// Configuracao do Jogador
	public static final int MAX_LIVES = 7;
	public static final int MAX_GUN_LEVEL = 3;
	public static final int SCORE_EXTRA_LIFE = 150;

	// Configuracao do Inimigo
	// VIDA
	public static final int LIVES_NORMAL = 1;
	public static final int LIVES_FAST = 1;
	public static final int LIVES_ARMED = 2;
	public static final int LIVES_ARMORED = 4;
	// VELOCIDADE
	public static final int SPEED_NORMAL = 5;
	public static final int SPEED_FAST = 10;
	public static final int SPEED_ARMORED = 2;
	public static final int SPEED_ARMED = 5;
	// PONTUACAO
	public static final int SCORE_NORMAL = 100;
	public static final int SCORE_FAST = 150;
	public static final int SCORE_ARMORED = 300;
	public static final int SCORE_ARMED = 200;

	// POWER-UPS
	public static final int HELMET_DURATION_MS = 8000;
	public static final int CLOCK_DURATION_MS = 7000;
	public static final int SHOVEL_DURATION_MS = 10000;

	// GUI
	public static final int TILE_SIZE = 45;

}
