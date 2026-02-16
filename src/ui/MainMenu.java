package ui;

import game.Game;
import game.persistence.GameSaveData;
import game.persistence.JsonSaveManager;
import ranking.RankingManager;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainMenu extends JFrame {

    private JPanel mainPanel;

    public MainMenu() {
        setTitle("Battle City OOP - Menu");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);

        showMainScreen();
    }

    private void showMainScreen() {
        if (mainPanel != null)
            remove(mainPanel);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("BATTLE CITY");
        title.setFont(new Font("Monospaced", Font.BOLD, 46));
        title.setForeground(Color.YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnNew = createButton("NOVO JOGO");
        JButton btnLoad = createButton("CONTINUAR JOGO");
        JButton btnRanking = createButton("VER RANKING");
        JButton btnExit = createButton("SAIR");

        btnNew.addActionListener(e -> showSetupScreen());
        btnExit.addActionListener(e -> System.exit(0));

        btnLoad.addActionListener(e -> {
            try {
                GameSaveData data = JsonSaveManager.loadGame();
                if (data == null) {
                    JOptionPane.showMessageDialog(this, "Nenhum jogo salvo encontrado!", "Continuar Jogo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    launchGame(false, null, null, 1);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "O ficheiro de save está corrompido.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRanking.addActionListener(e -> {
            RankingManager rankingManager = new ranking.RankingManager();
            RankingView.displayLeaderboard(rankingManager.getFormattedRanking());
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(btnNew);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnLoad);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnRanking);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnExit);

        add(mainPanel);
        revalidate();
        repaint();
    }

    private void showSetupScreen() {
        remove(mainPanel);

        mainPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        mainPanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("CONFIGURAÇÃO", SwingConstants.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 30));
        title.setForeground(Color.YELLOW);

        JLabel lblName = createLabel("NOME DO JOGADOR:");
        JTextField txtName = new JTextField();
        txtName.setFont(new Font("Monospaced", Font.BOLD, 16));

        JLabel lblMap = createLabel("ESCOLHA O MAPA:");
        String[] maps = { "Mapa Clássico",
                "Mapa Labirinto",
                "Mapa Fortaleza",
                "Aleatório" };
        JComboBox<String> cbMap = new JComboBox<>(maps);
        cbMap.setFont(new Font("Monospaced", Font.BOLD, 14));

        JLabel lblDiff = createLabel("DIFICULDADE:");
        String[] diffs = { "Fácil",
                "Médio",
                "Difícil" };
        JComboBox<String> cbDiff = new JComboBox<>(diffs);
        cbDiff.setFont(new Font("Monospaced", Font.BOLD, 14));

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(Color.BLACK);
        JButton btnBack = createButton("VOLTAR");
        JButton btnStart = createButton("INICIAR");

        btnBack.addActionListener(e -> showMainScreen());

        btnStart.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um nome para o tanque!", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int mapIndex = cbMap.getSelectedIndex();
            if (mapIndex == 3)
                mapIndex = new Random().nextInt(3);

            String mapPath = switch (mapIndex) {
                case 0 -> "src/grid/models/model_classic.txt";
                case 1 -> "src/grid/models/model_maze.txt";
                case 2 -> "src/grid/models/model_strength.txt";
                default -> "src/grid/models/model_classic.txt";
            };

            int diff = cbDiff.getSelectedIndex() + 1;

            launchGame(true, name, mapPath, diff);
        });

        btnPanel.add(btnBack);
        btnPanel.add(btnStart);

        mainPanel.add(title);
        mainPanel.add(lblName);
        mainPanel.add(txtName);
        mainPanel.add(lblMap);
        mainPanel.add(cbMap);
        mainPanel.add(lblDiff);
        mainPanel.add(cbDiff);
        mainPanel.add(btnPanel);

        add(mainPanel);
        revalidate();
        repaint();
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Monospaced", Font.BOLD, 14));
        lbl.setVerticalAlignment(SwingConstants.BOTTOM);
        return lbl;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 18));
        btn.setBackground(Color.DARK_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 45));
        return btn;
    }

    private void launchGame(boolean isNewGame, String name, String map, int diff) {
        this.dispose();
        new Thread(() -> {
            Game game = new Game();
            game.startGame(isNewGame, name, map, diff);
        }).start();
    }
}