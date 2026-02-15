package characters.exceptions;

public class PowerUpDependencyException extends PowerUpException {
    public PowerUpDependencyException(String item, String dependency) {
        super("O Power-Up '" + item + "' não pode ser ativado porque a referência a '" + dependency + "' é nula.");
    }
}
