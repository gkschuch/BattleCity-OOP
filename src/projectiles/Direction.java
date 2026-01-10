package projectiles;

public enum Direction {

    UP, DOWN, LEFT, RIGHT;

    // métodos especiais (getter e setters)
    
    public int getDx() {
        return switch (this) {
            case LEFT -> -1;
            case RIGHT -> 1;
            default -> 0;
        };
    }

    public int getDy() {
        return switch (this) {
            case UP -> -1;
            case DOWN -> 1;
            default -> 0;
        };
    }
}