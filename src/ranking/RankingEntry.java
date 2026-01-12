package ranking;

public class RankingEntry {
	//atributos

	private String name;
	private int    score;

	// contrutor

	public RankingEntry(String name, int score) {
		this.name  = name;
		this.score = score;
	}

	// métodos especiais (getters e setters)

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
}
