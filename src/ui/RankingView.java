package ui;

import javax.swing.*;
import ui.exceptions.UIException;
import java.awt.*;

public class RankingView {
    public static void displayLeaderboard(String formattedRanking) {
        if (formattedRanking == null || formattedRanking.trim().isEmpty()) {
            throw new UIException("Os dados do Ranking estão vazios ou nulos. Não é possível exibir o painel.");
        }
        JTextArea textArea = new JTextArea(formattedRanking);

        textArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        textArea.setEditable(false);
        textArea.setBackground(new Color(230, 230, 230));
        textArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 400));

        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "Battle City - Global Ranking",
                JOptionPane.PLAIN_MESSAGE);
    }
}
