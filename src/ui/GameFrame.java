package ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.InputController;
import ui.exceptions.UIException;

public class GameFrame extends JFrame {

	public GameFrame(InputController input) {
		if ( input == null ) {
			throw new UIException("O GameFrame não pode ser iniciado sem um InputController válido.");
		}

		setTitle("Battle City OOP - UFPel Edition");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(true);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char command = ' ';

				switch ( e.getKeyCode() ) {
					case KeyEvent.VK_W -> command = 'w';
					case KeyEvent.VK_S -> command = 's';
					case KeyEvent.VK_A -> command = 'a';
					case KeyEvent.VK_D -> command = 'd';
					case KeyEvent.VK_SPACE -> command = 'f';
					case KeyEvent.VK_Q -> command = 'q';
					case KeyEvent.VK_P -> command = 'p';
					case KeyEvent.VK_O -> command = 'o';
					case KeyEvent.VK_L -> command = 'l';
				}
				if ( command != ' ' )
					input.addCommand(command);
			}
		});

		SwingUtilities.invokeLater(() -> setExtendedState(JFrame.MAXIMIZED_BOTH));
	}
}