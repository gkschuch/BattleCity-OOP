package game.persistence.exceptions;

public class GameSaveException extends PersistenceException {
    public GameSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
