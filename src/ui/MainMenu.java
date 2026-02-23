package ui;

import game.Game;
import game.persistence.GameSaveData;
import game.persistence.JsonSaveManager;
import ranking.RankingManager;
import utils.FontManager;
import characters.TankPlayer;

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
		title.setFont(FontManager.getFont(46f));
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
		title.setFont(FontManager.getFont(30f));
		title.setForeground(Color.YELLOW);

		JLabel     lblName = createLabel("NOME DO JOGADOR:");
		JTextField txtName = new JTextField();
		txtName.setFont(FontManager.getFont(16f));
		txtName.setBackground(new Color(0, 0, 0));
		txtName.setForeground(Color.WHITE);
		txtName.setCaretColor(Color.WHITE);
		txtName.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		JLabel            lblMap = createLabel("ESCOLHA O MAPA:");
		String[]          maps   = {"Mapa Clássico", "Mapa Labirinto", "Mapa Fortaleza", "Aleatório"};
		JComboBox<String> cbMap  = new JComboBox<>(maps);
		cbMap.setFont(FontManager.getFont(14f));
		cbMap.setBackground(Color.BLACK);
		cbMap.setForeground(Color.WHITE);
		cbMap.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

		JLabel            lblDiff = createLabel("DIFICULDADE:");
		String[]          diffs   = {"Fácil", "Médio", "Difícil"};
		JComboBox<String> cbDiff  = new JComboBox<>(diffs);
		cbDiff.setFont(FontManager.getFont(14f));
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

	public static void showEndScreen(String title, String message, TankPlayer player) {
		JDialog dialog = new JDialog(( Frame ) null, "Fim de Jogo", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setSize(650, 500);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);

		BackgroundImagePanel backgroundPanel = new BackgroundImagePanel(BACKGROUND_IMAGE_PATH);
		dialog.setContentPane(backgroundPanel);

		RoundedPanel panel = new RoundedPanel(40, new Color(0, 0, 0, 200));
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(FontManager.getFont(40f));
		lblTitle.setForeground(Color.YELLOW);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblMsg = new JLabel(message);
		lblMsg.setFont(FontManager.getFont(18f));
		lblMsg.setForeground(Color.WHITE);
		lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblScore = new JLabel("Jogador: " + player.getPlayerName() + " | Pontuação: " + player.getScore());
		lblScore.setFont(FontManager.getFont(18f));
		lblScore.setForeground(Color.WHITE);
		lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnOk = new JButton("VOLTAR AO MENU");
		btnOk.setFont(FontManager.getFont(18f));
		btnOk.setBackground(new Color(200, 180, 0));
		btnOk.setForeground(Color.BLACK);
		btnOk.setOpaque(true);
		btnOk.setContentAreaFilled(true);
		btnOk.setFocusPainted(false);
		btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);

		btnOk.setMaximumSize(new Dimension(300, 50));

		btnOk.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2), BorderFactory.createEmptyBorder(10, 20, 10, 20)));

		btnOk.addActionListener(e -> {
			dialog.dispose();
			new MainMenu().setVisible(true);
		});

		panel.add(lblTitle);
		panel.add(Box.createVerticalStrut(30));
		panel.add(lblMsg);
		panel.add(Box.createVerticalStrut(20));
		panel.add(lblScore);
		panel.add(Box.createVerticalStrut(40));
		panel.add(btnOk);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx  = 0;
		gbc.gridy  = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		backgroundPanel.add(panel, gbc);
		dialog.setVisible(true);
	}

	public static boolean showWaveScreen(int waveConcluida, TankPlayer player) {

		final java.util.concurrent.atomic.AtomicBoolean continuar = new java.util.concurrent.atomic.AtomicBoolean(false);

		JDialog dialog = new JDialog(( Frame ) null, "Fim de Jogo", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setSize(650, 500);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);

		BackgroundImagePanel backgroundPanel = new BackgroundImagePanel(BACKGROUND_IMAGE_PATH);
		dialog.setContentPane(backgroundPanel);

		RoundedPanel panel = new RoundedPanel(40, new Color(0, 0, 0, 200));
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

		JLabel lblTitle = new JLabel("VITÓRIA!");
		lblTitle.setFont(FontManager.getFont(40f));
		lblTitle.setForeground(Color.YELLOW);
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblMsg = new JLabel("Wave " + waveConcluida + " concluída! Prepare-se para a próxima!");
		lblMsg.setFont(FontManager.getFont(18f));
		lblMsg.setForeground(Color.WHITE);
		lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblScore = new JLabel("Jogador: " + player.getPlayerName() + " | Pontuação: " + player.getScore());
		lblScore.setFont(FontManager.getFont(18f));
		lblScore.setForeground(Color.WHITE);
		lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel btnRow = new JPanel(new GridLayout(1, 2, 18, 0));
		btnRow.setOpaque(false);
		btnRow.setMaximumSize(new Dimension(520, 60));
		btnRow.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnContinue = new JButton("CONTINUAR");
		btnContinue.setFont(FontManager.getFont(18f));
		btnContinue.setBackground(new Color(200, 180, 0));
		btnContinue.setForeground(Color.BLACK);
		btnContinue.setOpaque(true);
		btnContinue.setContentAreaFilled(true);
		btnContinue.setFocusPainted(false);
		btnContinue.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnContinue.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2), BorderFactory.createEmptyBorder(10, 20, 10, 20)));

		JButton btnMenu = new JButton("VOLTAR AO MENU");
		btnMenu.setFont(FontManager.getFont(18f));
		btnMenu.setBackground(new Color(200, 180, 0));
		btnMenu.setForeground(Color.BLACK);
		btnMenu.setOpaque(true);
		btnMenu.setContentAreaFilled(true);
		btnMenu.setFocusPainted(false);
		btnMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnMenu.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2), BorderFactory.createEmptyBorder(10, 20, 10, 20)));

		btnContinue.addActionListener(e -> {
			continuar.set(true);
			dialog.dispose();
		});

		btnMenu.addActionListener(e -> {
			continuar.set(false);
			dialog.dispose();
			new MainMenu().setVisible(true);
		});

		btnRow.add(btnContinue);
		btnRow.add(btnMenu);

		panel.add(lblTitle);
		panel.add(Box.createVerticalStrut(30));
		panel.add(lblMsg);
		panel.add(Box.createVerticalStrut(20));
		panel.add(lblScore);
		panel.add(Box.createVerticalStrut(40));
		panel.add(btnRow);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx  = 0;
		gbc.gridy  = 0;
		gbc.anchor = GridBagConstraints.CENTER;

		backgroundPanel.add(panel, gbc);
		dialog.setVisible(true);

		return continuar.get();
	}

	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.WHITE);
		label.setFont(FontManager.getFont(14f));
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		return label;
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setFont(FontManager.getFont(18f));
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