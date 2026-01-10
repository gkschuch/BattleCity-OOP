package ranking;

import java.util.List;
import utils.GameConfig;

public class RankingManager {
    private List<RankingEntry> ranking;
    private RankingFileHandler fileHandler;

    public RankingManager() {
        this.fileHandler = new RankingFileHandler();
        this.ranking = fileHandler.load();
        sortRanking();
    }

    public void addEntry(String name, int score) {
        ranking.add(new RankingEntry(name, score));
        sortRanking();

        if (ranking.size() > GameConfig.MAX_ENTRIES) {
            ranking.remove(ranking.size() - 1);
        }

        fileHandler.save(ranking);
    }

    public void sortRanking() {
        if (ranking.size() > 1) {
            quicksort(0, ranking.size() - 1);
        }
    }

    private void quicksort(int low, int high) {
        if (low < high) {
            int pivotIndex = partition(low, high);

            quicksort(low, pivotIndex - 1);
            quicksort(pivotIndex + 1, high);
        }
    }

    private int partition(int low, int high) {
        int pivotScore = ranking.get(high).getScore();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (ranking.get(j).getScore() >= pivotScore) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        RankingEntry temp = ranking.get(i);
        ranking.set(i, ranking.get(j));
        ranking.set(j, temp);
    }

    public void printRanking() {
        System.out.println("BATTLE CITY RANKING - " + GameConfig.MAX_ENTRIES);
        int rank = 1;
        for (RankingEntry entry : ranking) {
            System.out.println(rank + ". " + entry.getName() + " - " + entry.getScore() + " pts");
            rank++;
        }
    }

}
