package characters.enemy.strategy;

import characters.enemy.EnemyTank;
import characters.TankPlayer;
import grid.Grid;
import utils.Direction;

public class PersuitBehaviorStrategy implements EnemyBehaviorStrategy {
    @Override
    public Direction determineDirection(EnemyTank tank, TankPlayer player, Grid grid) {
        int targetX = (int) player.getX();
        int targetY = (int) player.getY();
        int myX = (int) tank.getX();
        int myY = (int) tank.getY();

        // 1. Pequena chance de aleatoriedade para não ficar "burro" preso em cantos
        if (Math.random() < 0.10)
            return Direction.getRandom();

        // 2. Calcula as direções preferenciais (Eixo X ou Eixo Y)
        Direction primary = (Math.abs(targetX - myX) > Math.abs(targetY - myY))
                ? (targetX > myX ? Direction.RIGHT : Direction.LEFT)
                : (targetY > myY ? Direction.DOWN : Direction.UP);

        Direction secondary = (primary == Direction.RIGHT || primary == Direction.LEFT)
                ? (targetY > myY ? Direction.DOWN : Direction.UP)
                : (targetX > myX ? Direction.RIGHT : Direction.LEFT);

        // 3. Tenta a direção primária (o caminho mais curto)
        if (grid.isWalkable(myY + primary.getDy(), myX + primary.getDx())) {
            return primary;
        }

        // 4. Se a primária estiver bloqueada por parede/água, tenta a secundária
        if (grid.isWalkable(myY + secondary.getDy(), myX + secondary.getDx())) {
            return secondary;
        }

        // 5. Se ambas estiverem bloqueadas, mantém a direção atual para evitar girar
        // freneticamente
        return tank.getDirection();
    }
}
