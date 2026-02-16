package grid.exceptions;

public class OutOfBoundsException extends GridException {
    public OutOfBoundsException(int row, int col, int maxRow, int maxCol) {
        super("Posição inválida no mapa [" + row + ", " + col + "]. Os limites são [0-" + (maxRow - 1) + ", 0-"
                + (maxCol - 1) + "].");
    }
}
