package ui;

import game.Game;
import game.persistence.GameSaveData;
import game.persistence.JsonSaveManager;
import ranking.RankingManager;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MainMenu extends JFrame {

	private static final String BACKGROUND_IMAGE_PATH = "resources/picture_menu.png";

	private JPanel               mainPanel;
	private BackgroundImagePanel backgroundPanel;

	public MainMenu() {
		setTitle("Battle City OOP - Menu");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		backgroundPanel = new BackgroundImagePanel(BACKGROUND_IMAGE_PATH);
		setContentPane(backgroundPanel);

		showMainScreen();
	}

	private void showMainScreen() {
		if ( mainPanel != null )
			backgroundPanel.remove(mainPanel);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);

		JLabel title = new JLabel("BATTLE CITY");
		title.setFont(utils.FontManager.getFont(46f));
		title.setForeground(Color.YELLOW);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);

		RoundedPanel buttonsBox = new RoundedPanel(16, new Color(0, 0, 0, 160));
		buttonsBox.setOpaque(false);
		buttonsBox.setLayout(new BoxLayout(buttonsBox, BoxLayout.Y_AXIS));
		buttonsBox.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
		buttonsBox.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnNew     = createButton("NOVO JOGO");
		JButton btnLoad    = createButton("CONTINUAR JOGO");
		JButton btnRanking = createButton("VER RANKING");
		JButton btnExit    = createButton("SAIR");

		btnNew.addActionListener(e -> showSetupScreen());
		btnExit.addActionListener(e -> System.exit(0));

		btnLoad.addActionListener(e -> {
			try {
				GameSaveData data = JsonSaveManager.loadGame();
				if ( data == null ) {
					JOptionPane.showMessageDialog(this, "Nenhum jogo salvo encontrado!", "Continuar Jogo", JOptionPane.INFORMATION_MESSAGE);
				} else {
					launchGame(false, null, null, 1);
				}
			} catch ( Exception ex ) {
				JOptionPane.showMessageDialog(this, "O ficheiro de save está corrompido.", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRanking.addActionListener(e -> {
			RankingManager rankingManager = new ranking.RankingManager();
			RankingView.displayLeaderboard(rankingManager.getFormattedRanking());
		});

		Dimension buttonSize = new Dimension(300, 45);
		btnNew.setPreferredSize(buttonSize);
		btnLoad.setPreferredSize(buttonSize);
		btnRanking.setPreferredSize(buttonSize);
		btnExit.setPreferredSize(buttonSize);

		buttonsBox.add(btnNew);
		buttonsBox.add(Box.createRigidArea(new Dimension(0, 12)));
		buttonsBox.add(btnLoad);
		buttonsBox.add(Box.createRigidArea(new Dimension(0, 12)));
		buttonsBox.add(btnRanking);
		buttonsBox.add(Box.createRigidArea(new Dimension(0, 12)));
		buttonsBox.add(btnExit);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		mainPanel.add(title);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		mainPanel.add(buttonsBox);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx  = 0;
		gbc.gridy  = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		backgroundPanel.add(mainPanel, gbc);
		revalidate();
		repaint();
	}

	private void showSetupScreen() {

		if ( mainPanel != null )
			backgroundPanel.remove(mainPanel);

		mainPanel = new RoundedPanel(16, new Color(0, 0, 0, 160));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
		mainPanel.setOpaque(false);

		JPanel formPanel = new JPanel(new GridLayout(7, 1, 12, 12));
		formPanel.setOpaque(false);

		JLabel title = new JLabel("CONFIGURAÇÃO", SwingConstants.CENTER);
		title.setFont(utils.FontManager.getFont(30f));
		title.setForeground(Color.YELLOW);

		JLabel     lblName = createLabel("NOME DO JOGADOR:");
		JTextField txtName = new JTextField();
		txtName.setFont(utils.FontManager.getFont(16f));
		txtName.setBackground(new Color(0, 0, 0));
		txtName.setForeground(Color.WHITE);
		txtName.setCaretColor(Color.WHITE);
		txtName.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		JLabel            lblMap = createLabel("ESCOLHA O MAPA:");
		String[]          maps   = {"Mapa Clássico", "Mapa Labirinto", "Mapa Fortaleza", "Aleatório"};
		JComboBox<String> cbMap  = new JComboBox<>(maps);
		cbMap.setFont(utils.FontManager.getFont(14f));
		cbMap.setBackground(Color.BLACK);
		cbMap.setForeground(Color.WHITE);
		cbMap.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		JLabel            lblDiff = createLabel("DIFICULDADE:");
		String[]          diffs   = {"Fácil", "Médio", "Difícil"};
		JComboBox<String> cbDiff  = new JComboBox<>(diffs);
		cbDiff.setFont(utils.FontManager.getFont(14f));
		cbDiff.setBackground(Color.BLACK);
		cbDiff.setForeground(Color.WHITE);
		cbDiff.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		JPanel btnPanel = new JPanel(new GridLayout(1, 2, 25, 0));
		btnPanel.setOpaque(false);
		btnPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

		JButton btnBack  = createButton("VOLTAR");
		JButton btnStart = createButton("INICIAR");

		btnBack.setBackground(new Color(40, 40, 40));
		btnBack.setForeground(Color.WHITE);
		btnBack.setOpaque(true);
		btnBack.setContentAreaFilled(true);
		btnBack.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		btnStart.setBackground(new Color(200, 180, 0));
		btnStart.setForeground(Color.BLACK);
		btnStart.setOpaque(true);
		btnStart.setContentAreaFilled(true);
		btnStart.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		btnBack.addActionListener(e -> showMainScreen());

		btnStart.addActionListener(e -> {
			String name = txtName.getText().trim();
			if ( name.isEmpty() ) {
				JOptionPane.showMessageDialog(this, "Digite um nome para o tanque!", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			int mapIndex = cbMap.getSelectedIndex();
			if ( mapIndex == 3 )
				mapIndex = new Random().nextInt(3);

			String mapPath = switch ( mapIndex ) {
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

		formPanel.add(title);
		formPanel.add(lblName);
		formPanel.add(txtName);
		formPanel.add(lblMap);
		formPanel.add(cbMap);
		formPanel.add(lblDiff);
		formPanel.add(cbDiff);

		mainPanel.add(formPanel, BorderLayout.CENTER);
		mainPanel.add(btnPanel, BorderLayout.SOUTH);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx  = 0;
		gbc.gridy  = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		backgroundPanel.add(mainPanel, gbc);

		revalidate();
		repaint();
	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.WHITE);
		label.setFont(utils.FontManager.getFont(14f));
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		return label;
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(utils.FontManager.getFont(18f));
		button.setForeground(Color.WHITE);

		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);

		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(250, 45));

		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		return button;
	}

	private void launchGame(boolean isNewGame, String name, String map, int diff) {
		this.dispose();
		new Thread(() -> {
			Game game = new Game();
			game.startGame(isNewGame, name, map, diff);
		}).start();
	}
}