package grid.blocks;

public class Water extends Block {
    
    //contrutor, cria um tijolo em tal posição
    public Water(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isWalkable(){    //falso, tanque não anda por cima da água
        return false;
    }

    @Override
    public boolean isProjectilePassThrough(){   //true, o tiro pode passar a água
        return true;
    } 
}
