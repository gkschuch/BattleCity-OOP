package game.exceptions;

public class PlayerOutOfLivesException extends GameTerminationException {
    public PlayerOutOfLivesException() {
        super("GAME OVER: você ficou sem vidas.", false);
    }

}
