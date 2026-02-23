package ui;

import javax.swing.*;
import java.awt.*;
import grid.Grid;
import grid.blocks.Block;
import ui.exceptions.MissingRenderContextException;
import utils.GameConfig;
import characters.TankPlayer;
import characters.enemy.EnemyTank;
import game.Shot;
import java.util.List;

public class GamePanel extends JPanel {
    private Grid grid;
    private TankPlayer player;
    private List<EnemyTank> enemies;
    private List<Shot> shots;
    private Hud hud = new Hud();

    private boolean paused = false;
    private long timeRemainingMs = 60000;

    public GamePanel(Grid grid, TankPlayer player, List<EnemyTank> enemies, List<Shot> shots) {
        if (grid == null)
            throw new MissingRenderContextException("Grid");
        if (player == null)
            throw new MissingRenderContextException("TankPlayer");
        if (enemies == null)
            throw new MissingRenderContextException("Lista de Inimigos");
        if (shots == null)
            throw new MissingRenderContextException("Lista de Tiros");
        this.grid = grid;
        this.player = player;
        this.enemies = enemies;
        this.shots = shots;

        setPreferredSize(new Dimension(grid.getCols() * GameConfig.TILE_SIZE, grid.getRows() * GameConfig.TILE_SIZE));
        setBackground(Color.BLACK);
    }

    public void setTimeRemainingMs(long timeRemainingMs) {
        this.timeRemainingMs = timeRemainingMs;
    }

    public void updateReferences(Grid newGrid, TankPlayer newPlayer, List<EnemyTank> newEnemies) {
        if (newGrid == null)
            throw new MissingRenderContextException("New Grid (Load)");
        if (newPlayer == null)
            throw new MissingRenderContextException("New TankPlayer (Load)");
        if (newEnemies == null)
            throw new MissingRenderContextException("New Lista de Inimigos (Load)");

        this.grid = newGrid;
        this.player = newPlayer;
        this.enemies = newEnemies;

        int width = grid.getCols() * GameConfig.TILE_SIZE;
        int height = grid.getRows() * GameConfig.TILE_SIZE + 60;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isPaused()) {
            drawGameWorld(g);
            return;
        }
        drawPauseOverlay(g);
    }

    private void drawGameWorld(Graphics g) {
        int gameWidth = grid.getCols() * GameConfig.TILE_SIZE;
        int gameHeight = grid.getRows() * GameConfig.TILE_SIZE;

        int offsetX = (getWidth() - gameWidth) / 2;
        int offsetY = (getHeight() - gameHeight) / 2;

        if (offsetY < 30)
            offsetY = 30;
        if (offsetX < 0)
            offsetX = 0;
        g.setFont(utils.FontManager.getFont(24f));
        long seconds = timeRemainingMs / 1000;
        String timeStr = String.format("TEMPO: %02d:%02d", seconds / 60, seconds % 60);
        FontMetrics fm = g.getFontMetrics();
        int textX = offsetX + (gameWidth - fm.stringWidth(timeStr)) / 2;
        int textY = offsetY - 15;

        g.setColor(Color.DARK_GRAY);
        g.drawString(timeStr, textX + 2, textY + 2);

        if (seconds <= 10)
            g.setColor(Color.RED);
        else
            g.setColor(Color.WHITE);
        g.drawString(timeStr, textX, textY);
        g.translate(offsetX, offsetY);

        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                Block b = grid.getBlock(r, c);
                drawBlock(g, b, r, c);
            }
        }

        g.drawImage(player.getImage(),
                (int) (player.getX() * GameConfig.TILE_SIZE),
                (int) (player.getY() * GameConfig.TILE_SIZE),
                GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);

        for (EnemyTank e : enemies) {
            if (e != null && !e.isDestroyed()) {
                g.drawImage(e.getImage(),
                        (int) (e.getX() * GameConfig.TILE_SIZE),
                        (int) (e.getY() * GameConfig.TILE_SIZE),
                        GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, null);
            }
        }

        g.setColor(Color.YELLOW);
        synchronized (shots) {
            int bulletSize = 8;
            int offset = (GameConfig.TILE_SIZE - bulletSize) / 2;
            for (Shot s : shots) {
                g.fillOval(s.p.getX() * GameConfig.TILE_SIZE + offset,
                        s.p.getY() * GameConfig.TILE_SIZE + offset,
                        bulletSize, bulletSize);
            }
        }

        g.translate(-offsetX, -offsetY);
        int hudX = offsetX + gameWidth + 20;
        hud.drawRight(g, player, grid, getWidth(), getHeight(), hudX);
    }

    private void drawPauseOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(utils.FontManager.getFont(50f));
        FontMetrics fmTitle = g.getFontMetrics();
        String title = "JOGO PAUSADO";

        int titleX = (getWidth() - fmTitle.stringWidth(title)) / 2;
        int titleY = getHeight() / 2 - 60;

        g.setColor(Color.BLACK);
        g.drawString(title, titleX + 3, titleY + 3);
        g.setColor(Color.YELLOW);
        g.drawString(title, titleX, titleY);

        g.setFont(utils.FontManager.getFont(20f));
        FontMetrics fmOptions = g.getFontMetrics();
        g.setColor(Color.WHITE);

        String[] options = {
                "[ P ] RETOMAR JOGO",
                "[ O ] SALVAR PROGRESSO",
                "[ L ] CARREGAR JOGO",
                "[ Q ] SAIR DO JOGO"
        };

        int optionY = getHeight() / 2 + 10;
        for (String option : options) {
            int optionX = (getWidth() - fmOptions.stringWidth(option)) / 2;
            g.drawString(option, optionX, optionY);
            optionY += 35;
        }
    }

    private void drawBlock(Graphics g, Block b, int row, int col) {
        if (b == null)
            return;
        Image image = b.getImage();

        if (image != null) {
            g.drawImage(
                    image,
                    col * GameConfig.TILE_SIZE,
                    row * GameConfig.TILE_SIZE,
                    GameConfig.TILE_SIZE,
                    GameConfig.TILE_SIZE,
                    null);
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
