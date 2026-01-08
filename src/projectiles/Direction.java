package projectiles;

// Lista fixa de valores
// Define as 4 direções possíveis
public enum Direction {

    UP, DOWN, LEFT, RIGHT;

    // Retorna quanto mover no eixo X para esta direção
    public int getDx() {
        return switch (this) {
            case LEFT -> -1;
            case RIGHT -> 1;
            default -> 0;
        };
    }

    // Retorna quanto mover no eixo Y para esta direção
    public int getDy() {
        return switch (this) {
            case UP -> -1;
            case DOWN -> 1;
            default -> 0;
        };
    }
    // getDx() e getDy() convertem direção para números (+1, -1, 0)
}