package ui;

import utils.FontManager;

import javax.swing.*;
import java.awt.*;

public class RankingView {

	private static final String BACKGROUND_IMAGE_PATH = "resources/picture_menu.png";

	public static void displayLeaderboard(String leaderboardText) {

		JDialog dialog = new JDialog(( Frame ) null, "Battle City - Global Ranking", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setSize(520, 560);
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(null);

		BackgroundImagePanel backgroundPanel = new BackgroundImagePanel(BACKGROUND_IMAGE_PATH);
		dialog.setContentPane(backgroundPanel);

		RoundedPanel mainPanel = new RoundedPanel(16, new Color(0, 0, 0, 160));
		mainPanel.setOpaque(false);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

		JPanel formPanel = new JPanel(new GridLayout(2, 1, 12, 12));
		formPanel.setOpaque(false);

		JLabel title = new JLabel("RANKING", SwingConstants.CENTER);
		title.setFont(FontManager.getFont(30f));
		title.setForeground(Color.YELLOW);

		JTextArea area = new JTextArea(leaderboardText);
		area.setEditable(false);
		area.setOpaque(true);
		area.setBackground(new Color(0, 0, 0, 220));
		area.setForeground(Color.WHITE);
		area.setCaretColor(Color.WHITE);
		area.setFont(FontManager.getFont(16f));
		area.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		area.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scroll = new JScrollPane(area);
		scroll.setBorder(null);

		JPanel btnPanel = new JPanel(new GridLayout(1, 1, 10, 0));
		btnPanel.setOpaque(false);

		JButton btnOk = new JButton("VOLTAR");
		btnOk.setFont(FontManager.getFont(18f));
		btnOk.setBackground(new Color(200, 180, 0));
		btnOk.setForeground(Color.BLACK);
		btnOk.setOpaque(true);
		btnOk.setContentAreaFilled(true);
		btnOk.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		btnOk.setFocusPainted(false);
		btnOk.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnOk.addActionListener(e -> {
			dialog.dispose();
			boolean menuExists = false;

			for ( Frame frame : Frame.getFrames() ) {
				if ( frame.getClass().getSimpleName().equals("MainMenu") ) {
					frame.setVisible(true);
					frame.toFront();
					menuExists = true;
					break;
				}
			}

			if ( !menuExists ) {
				new MainMenu().setVisible(true);
			}

		});

		btnPanel.add(btnOk);

		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setOpaque(false);
		bottom.add(btnPanel, BorderLayout.CENTER);

		mainPanel.add(title, BorderLayout.NORTH);
		mainPanel.add(scroll, BorderLayout.CENTER);
		mainPanel.add(bottom, BorderLayout.SOUTH);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx  = 0;
		gbc.gridy  = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(12, 12, 12, 12);

		backgroundPanel.add(mainPanel, gbc);

		dialog.setVisible(true);
	}
}