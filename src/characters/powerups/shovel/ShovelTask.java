package characters.powerups.shovel;

import characters.exceptions.PowerUpDependencyException;
import grid.Grid;
import grid.blocks.Base;
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
            System.err.println("Shovel effect interrupted");
            Thread.currentThread().interrupt();
        } finally {
            updateWalls(false);
        }
    }

    private void updateWalls(boolean toSteel) {
        Base base = grid.getBase();
        if (base == null)
            return;

        int baseRow = base.getRow();
        int baseCol = base.getCol();

        for (int r = baseRow - 1; r <= baseRow + 1; r++)
            for (int c = baseCol - 1; c <= baseCol + 1; c++) {
                if (r == baseRow && c == baseCol)
                    continue;
                if (grid.isInside(r, c))
                    if (toSteel)
                        grid.setBlock(r, c, new Steel(r, c));
                    else
                        grid.setBlock(r, c, new Brick(r, c));
            }
    }
}