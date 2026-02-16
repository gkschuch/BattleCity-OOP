package game.exceptions;

public class AllEnemiesDestroyedException extends GameTerminationException {
    public AllEnemiesDestroyedException() {
        super("VITÓRIA: Todos os inimigos foram eliminados!", true);
    }

}
