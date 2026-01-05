package utils;

import java.util.Random;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    // metodo para sortear aleatoriamente um direcao
    public static Direction getRandom() {
        Direction[] values = Direction.values();
        return values[new Random().nextInt(values.length)];
    }
}
