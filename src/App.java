import game.Game;
import game.GameSetup;
import java.util.Scanner;
public class App {
	public static void main(String[] args) {
		Scanner   sc    = new Scanner(System.in);
		GameSetup setup = new GameSetup(sc);

		Game game = new Game(setup);
		game.run();

		sc.close();
	}
}
