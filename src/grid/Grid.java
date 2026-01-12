package grid;

import grid.blocks.Base;
import grid.blocks.Block;
import grid.blocks.Brick;
import grid.blocks.Steel;
import grid.blocks.Tree;
import grid.blocks.Water;

public final class Grid {
	// atributos

	private       int       rows;
	private       int       cols;
	private final Block[][] blocks;
	private       Base      base;

	// construtor

	public Grid() {
		this.rows   = 17;
		this.cols   = 13;
		this.blocks = new Block[rows][cols];
		initMap();
	}

	// métodos

	public void initMap() {
		for ( int r = 0; r < rows; r++ ) {
			for ( int c = 0; c < cols; c++ ) {
				blocks[r][c] = null;
			}
		}

		for ( int r = 0; r < rows; r++ ) {
			blocks[r][0]        = new Steel(r, 0);
			blocks[r][cols - 1] = new Steel(r, cols - 1);
		}
		for ( int c = 0; c < cols; c++ ) {
			blocks[0][c]        = new Steel(0, c);
			blocks[rows - 1][c] = new Steel(rows - 1, c);
		}

		// exemplo pra teste, tem que criar aleatório ou varios mapas

		blocks[5][3] = new Brick(5, 3);
		blocks[5][4] = new Brick(5, 4);

		blocks[3][6] = new Water(3, 6);

		blocks[4][2] = new Tree(4, 2);
		blocks[4][3] = new Tree(4, 3);

		blocks[13][6] = new Brick(13, 6);
		blocks[15][8] = new Brick(15, 8);
		blocks[12][7] = new Brick(12, 7);
		blocks[15][1] = new Brick(16, 2);

		// fechando a base com metal pra testes
		blocks[13][8] = new Steel(13, 8);
		blocks[15][8] = new Steel(15, 8);
		blocks[14][7] = new Steel(14, 7);
		blocks[14][9] = new Steel(14, 9);

		int baseRow = 14;
		int baseCol = 8;
		base                     = new Base(baseRow, baseCol);
		blocks[baseRow][baseCol] = base;
	}

	public boolean isInside(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols;
	}

	public boolean isWalkable(int row, int col) {
		if ( !isInside(row, col) )
			return false;
		Block b = blocks[row][col];
		return b == null || b.isWalkable();
	}

	public boolean canProjectilePass(int row, int col) {
		if ( !isInside(row, col) )
			return false;
		Block b = blocks[row][col];
		return b == null || b.isProjectilePassThrough();
	}

	// mantém compatibilidade com código antigo
	public boolean handleProjectileHit(int row, int col) {
		return handleProjectileHit(row, col, 1);
	}

	// dano configurável
	public boolean handleProjectileHit(int row, int col, int damage) {
		if ( !isInside(row, col) )
			return false;

		Block b = blocks[row][col];

		// vazio ou atravessável -> continua
		if ( b == null ) {
			return true;
		}
		if ( b.isProjectilePassThrough() ) {
			return true;
		}
		// sólido -> toma dano, projétil para
		b.takeDamage(damage);

		if ( b.isDestroyed() ) {
			blocks[row][col] = null;
		}

		return false;
	}

	public boolean isBaseDestroyed() {
		return base == null || base.isDestroyed();
	}

	// getters e setters
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
		if ( !isInside(row, col) )
			return null;
		return blocks[row][col];
	}

	public void setBlock(int row, int col, Block block) {
		if ( !isInside(row, col) )
			return;

		blocks[row][col] = block;

		if ( block != null && block.isBase() ) {
			base = ( Base ) block;
		}
	}
}
