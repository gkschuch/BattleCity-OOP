package projectiles.exceptions;

public class MissingProjectileDependencyException extends ProjectileException {
    public MissingProjectileDependencyException(String dependency) {
        super("O projétil não pode funcionar: a dependência '" + dependency + "' está nula.");
    }
}
