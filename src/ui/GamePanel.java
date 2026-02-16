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

        int gameWidth = grid.getCols() * GameConfig.TILE_SIZE;
        int gameHeight = grid.getRows() * GameConfig.TILE_SIZE;

        int offsetX = (getWidth() - gameWidth) / 2;
        int offsetY = (getHeight() - gameHeight) / 2;

        if (offsetY < 30) {
            offsetY = 30;
        }
        if (offsetX < 0) {
            offsetX = 0;
        }

        g.translate(offsetX, offsetY);

        for (int r = 0; r < grid.getRows(); r++) {
            for (int c = 0; c < grid.getCols(); c++) {
                Block b = grid.getBlock(r, c);
                drawBlock(g, b, r, c);
            }
        }

        g.setColor(Color.GREEN);
        g.fillRect((int) player.getX() * GameConfig.TILE_SIZE, (int) player.getY() * GameConfig.TILE_SIZE,
                GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);

        g.setColor(Color.RED);
        for (EnemyTank e : enemies) {
            if (e != null && !e.isDestroyed()) {
                g.fillRect((int) e.getX() * GameConfig.TILE_SIZE, (int) e.getY() * GameConfig.TILE_SIZE,
                        GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
            }
        }

        g.setColor(Color.YELLOW);
        synchronized (shots) {
            int bulletSize = 8;

            int offset = (GameConfig.TILE_SIZE - bulletSize) / 2;

            for (Shot s : shots) {
                g.fillOval(
                        s.p.getX() * GameConfig.TILE_SIZE + offset,
                        s.p.getY() * GameConfig.TILE_SIZE + offset,
                        bulletSize,
                        bulletSize);
            }

            g.translate(-offsetX, -offsetY);

            hud.draw(g, player, grid, getWidth(), getHeight());
        }
    }

    private void drawBlock(Graphics g, Block b, int row, int col) {
        if (b == null)
            return;

        g.setColor(b.getColor());

        g.fillRect(col * GameConfig.TILE_SIZE, row * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);

        g.setColor(g.getColor().darker());
        g.drawRect(col * GameConfig.TILE_SIZE, row * GameConfig.TILE_SIZE, GameConfig.TILE_SIZE, GameConfig.TILE_SIZE);
    }
}
