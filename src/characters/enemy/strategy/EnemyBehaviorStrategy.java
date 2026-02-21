package characters.enemy.strategy;

import characters.enemy.EnemyTank;
import characters.TankPlayer;
import grid.Grid;
import utils.Direction;

public interface EnemyBehaviorStrategy {
    Direction determineDirection(EnemyTank tank, TankPlayer player, Grid grid);

}
