package game.exceptions;

public class BaseDestroyedException extends GameTerminationException {
    public BaseDestroyedException() {
        super("GAME OVER: a base foi destruída.", false);
    }

}
