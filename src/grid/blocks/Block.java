package grid.blocks;

public class Block {
    protected int row; // linha em que o bloc está
    protected int col; // coluna em que o bloc está
    protected boolean destroyed; // diz se o bloco existe

    // contrutor, cria um bloco em tal posição (row, col)
    public Block(int row, int col) {
        this.setRow(row);
        this.setCol(col);
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

    public void setDestroyed(boolean destroyed){
        this.destroyed = destroyed;
    }

    // métodos (comportamentos o bloco)
    
    public boolean isDestroyed(){   //vê se o bloco existe
        return destroyed;
    }

    public void destroy(){          //destrói o bloco
        destroyed = true;
    }

    public boolean isWalkable() {   //diz se o tanque pode passar em cima
        return false;
    }

    public boolean isProjectilePassThrough() {  //vê se a bala pode passar em cima de um bloco
        return false;
    }

}
