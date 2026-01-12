package utils;

import java.util.Random;

public enum Direction_enum {
	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

	// atributos

	private final int dx, dy;

	// contrutor

	Direction_enum(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	// métodos

	public static Direction_enum getRandom() {
		Direction_enum[] values = Direction_enum.values();
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
