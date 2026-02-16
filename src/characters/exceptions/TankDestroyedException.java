package characters.exceptions;

public class TankDestroyedException extends CharacterException {
    public TankDestroyedException(String action) {
        super("Impossivel realizar " + action + ". O tanque esta destruido.");
    }
}
