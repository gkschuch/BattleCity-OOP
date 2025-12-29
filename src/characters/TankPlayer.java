package characters;

public class TankPlayer extends Tank {
    private String playerName;
    private int score;
    private int gunLevel;

    public TankPlayer(String name, int lives, int speed) {
        super(lives, speed);
        this.playerName = name;
        this.score = 0;
        this.gunLevel = 1; // Nivel padrao da arma
    }

    @Override
    public void shoot() {
        // Na versao final cria o objeto projetil
        System.out.println(this.playerName + " is shooting with a gun level " + this.gunLevel);
    }

    @Override
    public void onDestroy() {
        System.out.println("Player " + this.playerName + " was destroyed!");
        if (getlives() <= 0) {
            System.out.println("GAME OVER - Final Score: " + score);
        }
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void upgradeGun() {
        // So consegue upar ate o nivel 3
        if (this.gunLevel <= 3) {
            this.gunLevel++;
        }
    }

    // Getter

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

}
