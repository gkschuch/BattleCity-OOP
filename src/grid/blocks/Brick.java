package grid.blocks;

public class Brick extends Block {
    
    //contrutor, cria um tijolo em tal posição
    public Brick(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable(){    //falso, tanque não anda por cima do tijolo
        return false;
    }

    @Override
    public boolean isProjectilePassThrough(){   //falso, o tiro nao passa por um tijolo
        return false;
    }
    
}
