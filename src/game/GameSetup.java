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
		while ( true ) {
			System.out.print("Digite seu nome: ");
			String name = sc.nextLine();

			if ( name == null )
				continue;

			name = name.trim();

			if ( !name.isEmpty() )
				return name;

			System.out.println("Nome inválido. Tente de novo.\n");
		}
	}

	public int askDifficulty() {
		while ( true ) {
			System.out.println("Escolha a dificuldade:");
			System.out.println("1 - Fácil");
			System.out.println("2 - Médio");
			System.out.println("3 - Difícil");
			System.out.print("Digite 1, 2 ou 3: ");

			String op = sc.nextLine();

			if ( op == null )
				continue;

			op = op.trim();

			switch ( op ) {
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
