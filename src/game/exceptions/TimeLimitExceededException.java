package game.exceptions;

public class TimeLimitExceededException extends GameTerminationException {
    public TimeLimitExceededException() {
        super("GAME OVER: O tempo de 1 minuto acabou!", false);
    }
}