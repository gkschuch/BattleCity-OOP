package game;

import characters.TankPlayer;
import grid.Grid;
import ranking.RankingManager;
import ui.RankingView;

public class GameStateManager {
    private final RankingManager rankingManager = new RankingManager();

    protected boolean isGameOver(Grid grid, TankPlayer player, int enemiesAlive) {
        if (grid.isBaseDestroyed()) {
            System.out.println("\nGAME OVER: a base foi destruída.");
            return true;
        }
        if (player.getLives() <= 0) {
            System.out.println("\nGAME OVER: você ficou sem vidas.");
            return true;
        }
        if (enemiesAlive == 0) {
            System.out.println("\nVOCE VENCEU: todos os inimigos foram destruídos.");
            return true;
        }
        return false;
    }

    protected void finalizeGame(TankPlayer player) {
        rankingManager.addEntry(player.getPlayerName(), player.getScore());
        rankingManager.printRanking();
        RankingView.displayLeaderboard(rankingManager.getFormattedRanking());
    }

}
