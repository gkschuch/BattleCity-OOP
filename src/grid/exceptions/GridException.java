package grid.exceptions;

public class GridException extends RuntimeException {
    public GridException(String message) {
        super(message);
    }

    public GridException(String message, Throwable cause) {
        super(message, cause);
    }
}
