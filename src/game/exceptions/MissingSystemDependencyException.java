package game.exceptions;

public class MissingSystemDependencyException extends IllegalArgumentException {

    public MissingSystemDependencyException(String system, String dependency) {
        super("Falha crítica no sistema '" + system + "': O argumento '" + dependency + "' não pode ser nulo.");
    }
}