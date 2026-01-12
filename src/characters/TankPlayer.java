package characters;

import grid.Grid;
import projectiles.BasicProjectile;
import ranking.RankingManager;
import utils.GameConfig;

public class TankPlayer extends Tank {
    // atributos

    private final String playerName;
    private int score;
    private int gunLevel;
    private Grid grid;
    private BasicProjectile lastShot;
    private RankingManager rankingManager;

    // contrutor

    public TankPlayer(String name, double x, double y, int lives, double speed, RankingManager rankingManager) {
        if (lives >= GameConfig.MAX_LIVES) {
            lives = GameConfig.MAX_LIVES;
        }
        super(x, y, speed, lives);
        this.playerName = name;
        this.score = 0;
        this.gunLevel = 1;
        this.rankingManager = rankingManager;
    }

    // métodos

    private projectiles.Direction toProjectileDirection(utils.Direction d) {
        if (d == utils.Direction.UP)
            return projectiles.Direction.UP;
        if (d == utils.Direction.DOWN)
            return projectiles.Direction.DOWN;
        if (d == utils.Direction.LEFT)
            return projectiles.Direction.LEFT;
        return projectiles.Direction.RIGHT;
    }

    @Override
    public void shoot() {
        if (grid == null) {
            System.out.println("TankPlayer: grid nao configurado. Use player.setGrid(grid).");
            return;
        }

        if (lastShot != null && lastShot.isActive())
            return;

        projectiles.Direction pd = toProjectileDirection(this.getDirection());

        int startX = (int) this.getX() + pd.getDx(); // coluna
        int startY = (int) this.getY() + pd.getDy(); // linha

        BasicProjectile p = new BasicProjectile(startX, startY, pd);
        p.setGrid(grid); // ponte com o mapa
        p.start();

        lastShot = p;
    }

    @Override
    public void onDestroy() {
        System.out.println("Player " + this.getPlayerName() + " was destroyed!");
        if (this.getLives() <= 0) {
            System.out.println("GAME OVER - Final Score: " + this.getScore());
        } else {
            System.out.println("Lives remaining: " + this.getLives());
        }
    }

    public void addScore(int points) {
        this.score += points;
    }

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

    public void upgradeGun() {
        if (this.gunLevel < GameConfig.MAX_GUN_LEVEL) {
            this.gunLevel++;
        }
    }

    // métodos especiais (getters e setters)

    public int getScore() {
        return score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGunLevel() {
        return gunLevel;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
