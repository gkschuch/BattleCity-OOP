package game.exceptions;

public class InvalidShotParameterException extends IllegalArgumentException {
    public InvalidShotParameterException(String parameter, Object invalidValue) {
        super("Falha ao criar tiro: O parâmetro '" + parameter + "' tem um valor inválido (" + invalidValue + ").");
    }
}