package game;

import javax.swing.JOptionPane;
import java.util.Random;

public class GameSetup {
	public GameSetup() {
	}

	// métodos

	public String askPlayerName() {
		while (true) {
			String name = JOptionPane.showInputDialog(null, "Digite o seu nome", "Player Setup",
					JOptionPane.QUESTION_MESSAGE);

			if (name != null && !name.trim().isEmpty())
				return name.trim();

			JOptionPane.showMessageDialog(null, "Nome inválido. Tente de novo.", "ERROR", JOptionPane.ERROR_MESSAGE);
			System.out.println("\n");
		}
	}

	public String askMapChoice() {

		String[] options = { "Mapa Clássico", "Mapa Labirinto", "Mapa Fortaleza", "Aleatório" };

		int selection = JOptionPane.showOptionDialog(
				null,
				"Escolha o mapa:",
				"Seleção de mapas",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE,
				null,
				options,
				options[3]);
		if (selection == JOptionPane.CLOSED_OPTION)
			selection = 3;

		if (selection == 3)
			selection = new Random().nextInt(3);

		return switch (selection) {
			case 0 -> "src/grid/models/model_classic.txt";
			case 1 -> "src/grid/models/model_maze.txt";
			case 2 -> "src/grid/models/model_strength.txt";
			default -> "src/grid/models/model_classic.txt";
		};
	}

	public int askDifficulty() {
		String[] options = { "Fácil", "Médio", "Difícil" };
		int selection = JOptionPane.showOptionDialog(
				null,
				"Escolha a dificuldade:",
				"Seleção de dificuldade",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				options,
				options[0]);

		if (selection == JOptionPane.CLOSED_OPTION)
			return 1;
		return selection + 1;
	}
}
