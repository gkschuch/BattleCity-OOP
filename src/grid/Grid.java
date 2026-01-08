package grid;

import grid.blocks.Base;
import grid.blocks.Block;
import grid.blocks.Brick;
import grid.blocks.Steel;
import grid.blocks.Tree;
import grid.blocks.Water;

public class Grid {

    private int rows; // linhas do mapa
    private int cols; // colunas do mapa
    private Block[][] blocks;
    private Base base;

    // contrutor, inicia o mapa
    public Grid() {
        this.rows = 17;
        this.cols = 13;
        this.blocks = new Block[rows][cols];
        initMap();
    }

    // métodos especiais, getters e setters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Base getBase() {
        return base;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Block getBlock(int row, int col) {
        if (!isInside(row, col)) {
            return null;
        }
        return blocks[row][col];
    }

    public void setBlock(int row, int col, Block block) {
        if (!isInside(row, col)) {
            return;
        }
        blocks[row][col] = block;

        if (block != null && block.isBase()) {
            base = (Base) block;
        }
    }

    // métodos
    public void initMap() {

        for (int i = 0; i < rows; i++) {
            for (int t = 0; t < cols; t++) {
                blocks[i][t] = null;
            }
        }

        for (int i = 0; i < rows; i++) {
            blocks[i][0] = new Steel(i, 0);
            blocks[i][12] = new Steel(i, 12);
        }

        for (int i = 0; i < cols; i++) {
            blocks[0][i] = new Steel(0, i);
            blocks[16][i] = new Steel(16, i);

        }

        blocks[5][3] = new Brick(5, 3);
        blocks[5][4] = new Brick(5, 4);
        blocks[3][6] = new Water(3, 6);
        blocks[4][2] = new Tree(4, 2);
        blocks[4][3] = new Tree(4, 3);
        blocks[13][6] = new Brick(13, 6);
        blocks[15][8] = new Brick(15, 8);
        blocks[12][7] = new Brick(12, 7);

        int baseRow = 14;
        int baseCol = 8;
        base = new Base(baseRow, baseCol);
        blocks[baseRow][baseCol] = base;
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    public boolean isWalkable(int row, int col) {
        if (!isInside(row, col)) {
            return false;
        }
        Block b = blocks[row][col];
        return b == null || b.isWalkable(); // polimorfismo, pega de cada classe do bloco chamado
    }

    public boolean canProjectilePass(int row, int col) {
        if (!isInside(row, col))
            return false;
        Block b = blocks[row][col];
        return b == null || b.isProjectilePassThrough();
    }

    public boolean handleProjectileHit(int row, int col) {
        if (!isInside(row, col))
            return false;

        Block b = blocks[row][col];

        if (b == null || b.isProjectilePassThrough()) {
            return true;
        }

        b.takeDamage(1);

        if (b.isDestroyed()) {
            blocks[row][col] = null;
        }

        return false;
    }

    public boolean isBaseDestroyed() {
        return base == null || base.isDestroyed();
    }

}
