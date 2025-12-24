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

    // métodos
    public void initMap() {

        for (int i = 0; i < rows; i++) {
            for (int t = 0; t < cols; t++) {
                blocks[i][t] = null;
            }
        }

        for (int i = 0; i < rows; i++) {
            blocks[i][0] = new Steel(i, 0);
            blocks[i][cols - 1] = new Steel(i, cols - 1);
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
        int baseCol = 7;
        base = new Base(baseRow, baseCol);
        blocks[baseRow][baseCol] = base;
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }


}
