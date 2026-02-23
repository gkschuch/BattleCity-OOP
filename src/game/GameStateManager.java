package game;

import characters.TankPlayer;
import grid.Grid;
import ranking.RankingManager;
import game.exceptions.*;

public class GameStateManager {
    private final RankingManager rankingManager = new RankingManager();

    protected void checkGameState(Grid grid, TankPlayer player, int enemiesAlive, long elapsedTime)
            throws GameTerminationException {
        if (elapsedTime > 60000)
            throw new TimeLimitExceededException();
        if (grid.isBaseDestroyed())
            throw new BaseDestroyedException();
        if (player.getLives() <= 0)
            throw new PlayerOutOfLivesException();
    }

    protected void finalizeGame(TankPlayer player) {
        rankingManager.addEntry(player.getPlayerName(), player.getScore());
        rankingManager.printRanking();
    }

}
