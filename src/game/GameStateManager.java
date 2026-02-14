package game;

import characters.TankPlayer;
import grid.Grid;
import ranking.RankingManager;
import ui.RankingView;
import game.exceptions.*;

public class GameStateManager {
    private final RankingManager rankingManager = new RankingManager();

    protected void checkGameState(Grid grid, TankPlayer player, int enemiesAlive) throws GameTerminationException {
        if (grid.isBaseDestroyed())
            throw new BaseDestroyedException();

        if (player.getLives() <= 0)
            throw new PlayerOutOfLivesException();

        if (enemiesAlive == 0)
            throw new AllEnemiesDestroyedException();
    }

    protected void finalizeGame(TankPlayer player) {
        rankingManager.addEntry(player.getPlayerName(), player.getScore());
        rankingManager.printRanking();
        RankingView.displayLeaderboard(rankingManager.getFormattedRanking());
    }

}
