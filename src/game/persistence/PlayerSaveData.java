package game.persistence;

import utils.Direction;

public class PlayerSaveData {
    public String name;
    public double x;
    public double y;
    public double speed;
    public int score;
    public int lives;
    public int gunLevel;
    public Direction direction;

    public PlayerSaveData() {
    }

    public PlayerSaveData(String name, double x, double y, double speed, int lives, int score, int gunLevel,
            Direction direction) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.lives = lives;
        this.score = score;
        this.gunLevel = gunLevel;
        this.direction = direction;
    }
}
