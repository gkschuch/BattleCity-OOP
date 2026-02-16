package game.exceptions;

public abstract class GameTerminationException extends Exception {
    private final boolean victory;

    public GameTerminationException(String message, boolean victory) {
        super(message);
        this.victory = victory;
    }

    public boolean isVictory() {
        return victory;
    }
}
