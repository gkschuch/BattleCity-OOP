package game.persistence;

import characters.powerups.PowerUpType;

public class PowerUpSaveData {
    public PowerUpType type;
    public int row;
    public int col;

    public PowerUpSaveData() {
    }

    public PowerUpSaveData(PowerUpType type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }
}
