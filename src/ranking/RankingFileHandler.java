package ranking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import ranking.exceptions.InvalidRankingDataException;
import ranking.exceptions.RankingFileException;

public class RankingFileHandler {
	// atributo

	private static final String FILENAME = "ranking.txt";

	// métodos

	public void save(List<RankingEntry> ranking) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
			for (RankingEntry entry : ranking)
				writer.println(entry.getName() + ";" + entry.getScore());
		} catch (IOException e) {
			throw new RankingFileException("Não foi possível salvar o ranking no arquivo: " + FILENAME, e);
		}
	}

	public List<RankingEntry> load() {
		List<RankingEntry> list = new ArrayList<>();
		File file = new File(FILENAME);
		if (!file.exists())
			return list;

		try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
			String line;
			int lineCount = 0;
			while ((line = reader.readLine()) != null) {
				lineCount++;
				String[] parts = line.split(";");
				if (parts.length != 2) {
					throw new InvalidRankingDataException(
							"Formato inválido na linha " + lineCount + " do arquivo de ranking.");
				}
				try {
					list.add(new RankingEntry(parts[0], Integer.parseInt(parts[1])));
				} catch (NumberFormatException e) {
					throw new InvalidRankingDataException("Pontuação inválida na linha " + lineCount + ": " + parts[1]);
				}
			}
		} catch (IOException e) {
			throw new RankingFileException("Erro ao ler o arquivo de ranking.", e);
		}
		return list;
	}
}
