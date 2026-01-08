package grid.blocks;

import utils.Destructible;

public class Block implements Destructible {
    protected int row; // linha em que o bloco está
    protected int col; // coluna em que o bloco está
    protected int health; // vida do bloco
    protected boolean destroyed; // diz se o bloco existe

    // construtor, cria um bloco em tal posição (row, col)
    public Block(int row, int col) {
        this.setRow(row);
        this.setCol(col);
        this.health = 1;
        this.destroyed = false;
    }

    // métodos especiais (getters e setters)
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public boolean getDestroyed() {
        return this.destroyed;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // métodos (comportamentos do bloco)

    @Override
    public void takeDamage(int damage) {
        if (destroyed)
            return;

        this.health -= damage;
        if (this.health <= 0) {
            onDestroy();
        }
    }

    @Override
    public boolean isDestroyed() { // vê se o bloco existe
        return destroyed;
    }

    @Override
    public void onDestroy() {
        this.destroyed = true;
        System.out.println("Block destroyed in: [" + row + "," + col + "]");
    }

    public boolean isWalkable() { // diz se o tanque pode passar em cima
        return false;
    }

    public boolean isProjectilePassThrough() { // vê se a bala pode passar em cima de um bloco
        return false;
    }

    public boolean isBase() {
        return false;
    }
}
