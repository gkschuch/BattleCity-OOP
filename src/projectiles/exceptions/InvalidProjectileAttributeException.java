package projectiles.exceptions;

public class InvalidProjectileAttributeException extends ProjectileException {
    public InvalidProjectileAttributeException(String attribute, int value) {
        super("O atributo '" + attribute + "' tem um valor inválido: " + value);
    }
}