package ranking;

import java.util.List;

import utils.GameConfig;

public class RankingManager {
	// atributos

	private List<RankingEntry> rankingList;
	private RankingFileHandler fileHandler;

	// métodos

	public RankingManager() {
		this.fileHandler = new RankingFileHandler();
		this.rankingList = fileHandler.load();
		sortRanking();
	}

	public void addEntry(String name, int score) {
		rankingList.add(new RankingEntry(name, score));
		sortRanking();

		if (rankingList.size() > GameConfig.MAX_ENTRIES) {
			rankingList.remove(rankingList.size() - 1);
		}

		fileHandler.save(rankingList);
	}

	public void sortRanking() {
		if (rankingList.size() > 1) {
			quicksort(0, rankingList.size() - 1);
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
		int pivotScore = rankingList.get(high).getScore();
		int i = low - 1;
		for (int j = low; j < high; j++) {
			if (rankingList.get(j).getScore() >= pivotScore) {
				i++;
				swap(i, j);
			}
		}
		swap(i + 1, high);
		return i + 1;
	}

	private void swap(int i, int j) {
		RankingEntry temp = rankingList.get(i);
		rankingList.set(i, rankingList.get(j));
		rankingList.set(j, temp);
	}

	public void printRanking() {
		System.out.println("BATTLE CITY RANKING - " + GameConfig.MAX_ENTRIES);
		int rank = 1;
		for (RankingEntry entry : rankingList) {
			System.out.println(rank + ". " + entry.getName() + " - " + entry.getScore() + " pts");
			rank++;
		}
	}

	public String getFormattedRanking() {
		StringBuilder builder = new StringBuilder();
		builder.append("BATTLE CITY RANKING - ").append(GameConfig.MAX_ENTRIES).append("\n\n");

		int rank = 1;
		for (RankingEntry entry : rankingList) {
			builder.append(String.format("%d. %-15s %d pts\n",
					rank, entry.getName(), entry.getScore()));
			rank++;
		}
		return builder.toString();
	}

}
