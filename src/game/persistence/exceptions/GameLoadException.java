package game.persistence.exceptions;

public class GameLoadException extends PersistenceException {
    public GameLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
