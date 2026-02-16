package characters.powerups.shovel;

import characters.exceptions.PowerUpDependencyException;
import grid.Grid;
import grid.blocks.Brick;
import grid.blocks.Steel;
import utils.GameConfig;

public class ShovelTask implements Runnable {
    private final Grid grid;

    public ShovelTask(Grid grid) {
        if (grid == null)
            throw new PowerUpDependencyException("SHOVEL_TASK", "Grid");
        this.grid = grid;
    }

    @Override
    public void run() {
        try {
            this.updateWalls(true);
            Thread.sleep(GameConfig.SHOVEL_DURATION_MS);
        } catch (InterruptedException e) {
            System.err.println("Shovel effect interrupted"); // Tratamento de exceção
            Thread.currentThread().interrupt();
        } finally {
            updateWalls(false);
        }

    }

    private void updateWalls(boolean toSteel) {
        int[][] positions = {
                { 13, 7 }, { 13, 8 }, { 13, 9 },
                { 14, 7 }, { 14, 9 },
                { 15, 7 }, { 15, 8 }, { 15, 9 }
        };
        for (int[] pos : positions) {
            int r = pos[0];
            int c = pos[1];

            if (toSteel) {
                grid.setBlock(r, c, new Steel(r, c));
            } else {
                grid.setBlock(r, c, new Brick(r, c));
            }
        }
    }
}
