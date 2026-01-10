package grid.blocks;

public class Brick extends Block {
    
    //contrutor

    public Brick(int row, int col) {
        super(row, col);
    }

    // métodos

    @Override
    public boolean isWalkable(){    
        return false;
    }

    @Override
    public boolean isProjectilePassThrough(){   
        return false;
    }
    
}
