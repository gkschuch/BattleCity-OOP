package game;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class GameSetup {
	// atributos

	private final Scanner sc;
	private final Random  random;

	// construtor

	public GameSetup(Scanner sc) {
		this.sc     = sc;
		this.random = new Random();
	}

	// métodos

	public int sorteioNumeroX() {
		int x = random.nextInt(16) + 1;
		return x;
	}

	public int sorteioNumeroY() {
		int y = random.nextInt(16) + 1;
		return y;
	}

	public boolean posicaoLivre(int x, int y, String arquivoMapa) {
		try {
			BufferedReader br         = new BufferedReader(new FileReader(arquivoMapa));
			String         linha;
			int            linhaAtual = 0;

			while ( (linha = br.readLine()) != null ) {
				if ( linhaAtual == y ) {
					br.close();
					if ( linha.charAt(x) == '.' ) {
						return true;
					}
					return false;
				}
				linhaAtual++;
			}
			br.close();

		} catch ( IOException e ) {
			System.out.println("Erro de leitura de arquivo");
			return false;
		}
		return false;
	}

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

	public String askMapChoice() {
		System.out.println("\nEscolha o mapa:");
		System.out.println("1 - Mapa Clássico");
		System.out.println("2 - Mapa Labirinto");
		System.out.println("3 - Mapa Fortaleza");
		System.out.println("4 - Aleatório");
		System.out.print("Escolhba: ");

		int number = sc.nextInt();
		sc.nextLine();

		if ( number == 4 ) {
			number = ( int ) (Math.random() * 3) + 1;
		}
		switch ( number ) {
			case 1:
				return "grid/models/model_classic.txt";
			case 2:
				return "grid/models/model_maze.txt";
			case 3:
				return "grid/models/model_strength.txt";
			default:
				return "grid/models/model_classic.txt";
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
