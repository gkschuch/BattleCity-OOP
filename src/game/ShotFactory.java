package game;

import projectiles.BasicProjectile;

public class ShotFactory {
    // construtor

    private ShotFactory() {
    }

    // métodos

    public static BasicProjectile createShotFromTank(double tankX, double tankY, utils.Direction tankDir) {
        projectiles.Direction pd = toProjectileDirection(tankDir);

        int startX = (int) tankX;
        int startY = (int) tankY;

        return new BasicProjectile(startX, startY, pd);
    }

    public static projectiles.Direction toProjectileDirection(utils.Direction d) {
        if (d == null)
            return projectiles.Direction.RIGHT;

        return switch (d) {
            case UP -> projectiles.Direction.UP;
            case DOWN -> projectiles.Direction.DOWN;
            case LEFT -> projectiles.Direction.LEFT;
            default -> projectiles.Direction.RIGHT;
        };
    }

}
