package game;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import grid.Grid;
import java.util.List;

public class MovementSystem {
    // construtor

    private MovementSystem() {
    }

    // métodos

    public static void tryMovePlayer(Grid grid, TankPlayer p, utils.Direction dir, List<EnemyTank> enemies) {
        p.setDirection(dir);

        int row = (int) p.getY();
        int col = (int) p.getX();

        int newRow = row + dir.getDy();
        int newCol = col + dir.getDx();

        if (!grid.isInside(newRow, newCol))
            return;

        if (!grid.isWalkable(newRow, newCol))
            return;

        if (isEnemyAt(enemies, newRow, newCol))
            return;

        p.setY(newRow);
        p.setX(newCol);
    }

    public static void stepEnemy(Grid grid, EnemyTank e, TankPlayer player, List<EnemyTank> enemies) {
        int row = (int) e.getY();
        int col = (int) e.getX();

        utils.Direction dir = e.getDirection();
        int newRow = row + dir.getDy();
        int newCol = col + dir.getDx();

        if (!grid.isInside(newRow, newCol) || !grid.isWalkable(newRow, newCol)) {
            e.setDirection(utils.Direction.getRandom());
            return;
        }

        if (isEnemyAt(enemies, newRow, newCol))
            return;

        if (isPlayerAt(player, newRow, newCol)) {
            player.takeDamage(1);
            return;
        }

        e.setY(newRow);
        e.setX(newCol);
    }

    public static boolean isPlayerAt(TankPlayer p, int row, int col) {
        return ((int) p.getY() == row && (int) p.getX() == col);
    }

    public static boolean isEnemyAt(List<EnemyTank> enemies, int row, int col) {
        for (int i = 0; i < enemies.size(); i++) {
            EnemyTank e = enemies.get(i);

            if (e == null)
                continue;

            if (e.isDestroyed())
                continue;

            if ((int) e.getY() == row && (int) e.getX() == col)
                return true;
        }

        return false;
    }

    public static int countAlive(List<EnemyTank> enemies) {
        int n = 0;

        for (int i = 0; i < enemies.size(); i++) {
            EnemyTank e = enemies.get(i);

            if (e == null)
                continue;

            if (!e.isDestroyed())
                n++;
        }

        return n;
    }

}
