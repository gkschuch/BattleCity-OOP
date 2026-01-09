package utils;

import java.util.Random;

public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    // atributos

    private final int dx, dy;

    // contrutor

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // métodos
    
    public static Direction getRandom() {
        Direction[] values = Direction.values();
        return values[new Random().nextInt(values.length)];
    }

    // métodos especiais (getters e setters)

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
