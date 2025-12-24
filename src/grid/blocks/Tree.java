package grid.blocks;

public class Tree extends Block{
    
    //contrutor, cria uma árvore em tal posição
    public Tree(int row, int col){
        super(row, col);
    }

    @Override
    public boolean isWalkable() {    //tanque pode passar por cima da árvore
        return true;
    }

    @Override
    public boolean isProjectilePassThrough() {   //projétil também atravessa a árvore

        return true;
    }
}

