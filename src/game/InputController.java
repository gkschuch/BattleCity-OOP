package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import grid.Grid;
import utils.Direction;
import java.util.List;

public class InputController {
    private volatile char bufferedCommand = 0;
    private boolean running = true;

    public void addCommand(char command) {
        this.bufferedCommand = Character.toLowerCase(command);
        if (this.bufferedCommand == 'q')
            this.running = false;
    }

    public void processInput(TankPlayer player, Grid grid, List<EnemyTank> enemies, List<Shot> shots) {
        char currentCommand = pollCommand();
        if (currentCommand == 0)
            return;

        switch (currentCommand) {
            case 'w' -> MovementSystem.tryMovePlayer(grid, player, Direction.UP, enemies);
            case 's' -> MovementSystem.tryMovePlayer(grid, player, Direction.DOWN, enemies);
            case 'a' -> MovementSystem.tryMovePlayer(grid, player, Direction.LEFT, enemies);
            case 'd' -> MovementSystem.tryMovePlayer(grid, player, Direction.RIGHT, enemies);
            case 'f' -> ShotSystem.playerShoot(shots, grid, player);
        }
    }

    private char pollCommand() {
        char c = bufferedCommand;
        bufferedCommand = 0;
        return c;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }
}