package characters.exceptions;

public class DependencyMissingException extends CharacterException {
    public DependencyMissingException(String dependency) {
        super("Erro de configuração: Referencia a '" + dependency + "' está em falta.");
    }
}
