package game;

import java.util.Scanner;

public class GameSetup {
	// atributos

	private final Scanner sc;

	// construtor

	public GameSetup(Scanner sc) {
		this.sc = sc;
	}

	// métodos

	public String askPlayerName() {
		while (true) {
			System.out.print("Digite seu nome: ");
			String name = sc.nextLine();

			if (name == null)
				continue;

			name = name.trim();

			if (!name.isEmpty())
				return name;

			System.out.println("Nome inválido. Tente de novo.\n");
		}
	}

	public String askMapChoice() {
		System.out.println("\nEscolha o mapa:");
		System.out.println("1 - Mapa Clássico");
		System.out.println("2 - Mapa Labirinto");
		System.out.println("3 - Mapa Fortaleza");
		System.out.println("4 - Aleatório");
		System.out.print("Escolhba: ");

		int number = sc.nextInt();
		sc.nextLine();

		if (number == 4) {
			number = (int) (Math.random() * 3) + 1;
		}
		switch (number) {
			case 1:
				return "src/grid/models/model_classic.txt";
			case 2:
				return "src/grid/models/model_maze.txt";
			case 3:
				return "src/grid/models/model_strength.txt";
			default:
				return "src/grid/models/model_classic.txt";
		}
	}

	public int askDifficulty() {
		while (true) {
			System.out.println("Escolha a dificuldade:");
			System.out.println("1 - Fácil");
			System.out.println("2 - Médio");
			System.out.println("3 - Difícil");
			System.out.print("Digite 1, 2 ou 3: ");

			String op = sc.nextLine();

			if (op == null)
				continue;

			op = op.trim();

			switch (op) {
				case "1":
					return 1;
				case "2":
					return 2;
				case "3":
					return 3;
				default:
					System.out.println("Opção inválida. Tente de novo.\n");
			}
		}
	}

}
