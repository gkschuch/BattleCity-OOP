package game.persistence;

import utils.Direction;
import characters.enemy.EnemyTankType;

public class EnemySaveData {
    public EnemyTankType type;
    public double x;
    public double y;
    public int lives;
    public boolean frozen;
    public Direction direction;

    public EnemySaveData() {
    }

    public EnemySaveData(EnemyTankType type, double x, double y, int lives, boolean frozen, Direction direction) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.lives = lives;
        this.frozen = frozen;
        this.direction = direction;
    }

}
