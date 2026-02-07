package ui;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.InputController;

public class GameFrame extends JFrame {

    public GameFrame(InputController input) {
        setTitle("Battle City OOP - UFPel Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char command = ' ';
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> command = 'w';
                    case KeyEvent.VK_S -> command = 's';
                    case KeyEvent.VK_A -> command = 'a';
                    case KeyEvent.VK_D -> command = 'd';
                    case KeyEvent.VK_SPACE -> command = 'f';
                    case KeyEvent.VK_Q -> command = 'q';
                    case KeyEvent.VK_P -> command = 'p';
                    case KeyEvent.VK_O -> command = 'o';
                }
                if (command != ' ')
                    input.addCommand(command);
            }
        });
    }
}
