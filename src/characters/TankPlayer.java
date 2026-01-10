package characters;

import utils.GameConfig;
import ranking.RankingManager;

public class TankPlayer extends Tank {
    private String playerName;
    private int score;
    private int gunLevel; // Nível do tiro, evoluído via power-up de Estrela
    private RankingManager rankingManager;

    // Construtor do tanque do jogador
    public TankPlayer(String name, double x, double y, int lives, double speed, RankingManager rankingManager) {
        if (lives >= GameConfig.MAX_LIVES) {
            lives = GameConfig.MAX_LIVES;
        }
        super(x, y, speed, lives); // Chama o construtor da classe pai (Tank)
        this.playerName = name;
        this.score = 0; // Comeca com uma pontuacao zerada
        this.gunLevel = 1; // Nível inicial da arma
        this.rankingManager = rankingManager;
    }

    // Implementa o disparo do jogador
    @Override
    public void shoot() {
        // Na versao final cria um projetil
        System.out.println(this.getPlayerName() + " is shooting with a gun level " + this.getGunLevel());
    }

    // O metodo e chamado toda a vez que o tanque e destruido
    // Se nao tem mais vidas manda a mensagem de game over
    // senao mostra o numero de vidas restante
    @Override
    public void onDestroy() {
        System.out.println("Player " + this.getPlayerName() + " was destroyed!");

        if (this.getLives() <= 0) {
            System.out.println("GAME OVER - Final Score: " + this.getScore());
            this.rankingManager.addEntry(this.getPlayerName(), this.getScore());
        } else {
            System.out.println("Lives remaining: " + this.getLives());
        }
    }

    // Incrementa a pontuacao
    public void addScore(int points) {
        this.score += points;
    }

    // Metodo para adicionar vida ao jogador
    // Se ele chegar no numero maximo de cidas ele ganha pontos a mais
    public void addLives() {
        int currentLives = this.getLives();

        if (currentLives < GameConfig.MAX_LIVES) {
            this.setLives(currentLives + 1);
            System.out.println("Extra life obtained! Current lives: " + this.getLives());
        } else {
            this.addScore(GameConfig.SCORE_EXTRA_LIFE);
            System.out.println("Max lives reached! Bonus points awarded instead.");
        }
    }

    // Implementa a mecanica da estrela(Power up)
    // Atualiza a arma
    public void upgradeGun() {
        // So consegue upar ate o nivel 3
        if (this.gunLevel <= GameConfig.MAX_GUN_LEVEL) {
            this.gunLevel++;
        }
    }

    // Getters

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGunLevel() {
        return gunLevel;
    }
}