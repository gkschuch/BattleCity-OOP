package utils;

import java.util.Random;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getRandom() {
        Direction[] values = Direction.values();
        return values[new Random().nextInt(values.length)];
    }

}
