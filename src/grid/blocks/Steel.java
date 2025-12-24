package grid.blocks;

public class Steel extends Block {


    //contrutor, cra o aço em tal posição
    public Steel(int row, int col){
        super(row, col);
    }

    @Override
    public boolean isWalkable(){   //tanque nao passa em cima do aço
        return false;
    }

    @Override
    public boolean isProjectilePassThrough(){  //tiro nao passa pelo aço
        return false;
    }
}


