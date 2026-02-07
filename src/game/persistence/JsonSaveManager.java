package game.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import characters.TankPlayer;
import characters.enemy.EnemyTank;
import characters.powerups.PowerUp;
import grid.Grid;
import java.io.*;
import java.util.List;

public class JsonSaveManager {
    private static final String FILE_NAME = "savegame.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveGame(TankPlayer player, List<EnemyTank> enemies,
            List<PowerUp> powerUps, Grid grid, int difficulty, String mapPath) {
        GameSaveData data = new GameSaveData();

        data.mapPath = mapPath;
        data.difficulty = difficulty;
        data.player = new PlayerSaveData(
                player.getPlayerName(),
                player.getX(),
                player.getY(),
                player.getSpeed(),
                player.getLives(),
                player.getScore(),
                player.getGunLevel(),
                player.getDirection());

        for (EnemyTank enemyTank : enemies) {
            data.enemies.add(new EnemySaveData(
                    enemyTank.getEnemyTankType(),
                    enemyTank.getX(),
                    enemyTank.getY(),
                    enemyTank.getLives(),
                    enemyTank.isFrozen(),
                    enemyTank.getDirection()));
        }

        for (PowerUp powerUp : powerUps) {
            data.powerUps.add(new PowerUpSaveData(powerUp.getPowerUpType(),
                    powerUp.getRow(),
                    powerUp.getCol()));
        }
        char[][] rawLayout = grid.getCurrentLayout();
        String[] stringLayout = new String[rawLayout.length];
        for (int i = 0; i < rawLayout.length; i++) {
            stringLayout[i] = new String(rawLayout[i]);
        }
        data.gridLayout = stringLayout;

        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(data, writer);
            System.out.println("Jogo salvo com sucesso em " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("FALHA EM SALVAR O JOGOz: " + e.getMessage());
        }
    }

    public static GameSaveData loadGame() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return null;

        try (FileReader reader = new FileReader(FILE_NAME)) {
            return gson.fromJson(reader, GameSaveData.class);
        } catch (IOException e) {
            System.err.println("FALHA EM CARREGAR O JOGO: " + e.getMessage());
            return null;
        }
    }
}
