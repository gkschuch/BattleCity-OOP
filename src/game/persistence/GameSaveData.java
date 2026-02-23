package game.persistence;

import java.util.ArrayList;
import java.util.List;

public class GameSaveData {
    public String mapPath;
    public int difficulty;

    public PlayerSaveData player;

    public long elapsedTime;

    public List<EnemySaveData> enemies = new ArrayList<>();
    public List<PowerUpSaveData> powerUps = new ArrayList<>();

    public String[] gridLayout;

    public GameSaveData() {
    }
}
