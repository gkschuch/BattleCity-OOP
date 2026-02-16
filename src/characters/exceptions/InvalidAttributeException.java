package characters.exceptions;

public class InvalidAttributeException extends CharacterException {
    public InvalidAttributeException(String attr, Object val) {
        super("Atributo " + attr + "Invalido: " + val);
    }
}
