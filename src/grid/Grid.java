package grid;

import grid.blocks.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Grid {
	// atributos

	private int rows;
	private int cols;
	private final Block[][] blocks;
	private Base base;

	// construtor

	public Grid(String mapFile) {
		this.rows = 13;
		this.cols = 17;
		this.blocks = new Block[rows][cols];
		loadMapFromFile(mapFile);
	}

	// métodos

	private void loadMapFromFile(String mapFile) {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				blocks[r][c] = null;
			}
		}

		try (BufferedReader br = new BufferedReader(new FileReader(mapFile))) {
			String line;
			int row = 0;

			while ((line = br.readLine()) != null && row < rows) {
				for (int col = 0; col < line.length() && col < cols; col++) {
					char tile = line.charAt(col);
					createBlockFromChar(row, col, tile);
				}
				row++;
			}

		} catch (IOException e) {
			System.err.println("Erro ao carregar mapa: " + mapFile);
			e.printStackTrace();
			initDefaultMap();
		}
	}

	private void createBlockFromChar(int row, int col, char tile) {
		switch (tile) {
			case '#' -> blocks[row][col] = new Steel(row, col);
			case 'B' -> blocks[row][col] = new Brick(row, col);
			case '~' -> blocks[row][col] = new Water(row, col);
			case 'T' -> blocks[row][col] = new Tree(row, col);
			case 'X' -> {
				base = new Base(row, col);
				blocks[row][col] = base;
			}
			case '.' -> blocks[row][col] = null;
			default -> blocks[row][col] = null;
		}
	}

	private void initDefaultMap() {
		for (int r = 0; r < rows; r++) {
			blocks[r][0] = new Steel(r, 0);
			blocks[r][cols - 1] = new Steel(r, cols - 1);
		}
		for (int c = 0; c < cols; c++) {
			blocks[0][c] = new Steel(0, c);
			blocks[rows - 1][c] = new Steel(rows - 1, c);
		}

		int baseRow = 14;
		int baseCol = 6;
		base = new Base(baseRow, baseCol);
		blocks[baseRow][baseCol] = base;
	}

	public char[][] getCurrentLayout() {
		char[][] layout = new char[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (blocks[r][c] == null)
					layout[r][c] = '.';
				else
					layout[r][c] = blocks[r][c].getIcon();
			}
		}
		return layout;
	}

	public boolean isInside(int row, int col) {
		return row >= 0 && row < rows && col >= 0 && col < cols;
	}

	public boolean isWalkable(int row, int col) {
		if (!isInside(row, col))
			return false;
		Block b = blocks[row][col];
		return b == null || b.isWalkable();
	}

	public boolean canProjectilePass(int row, int col) {
		if (!isInside(row, col))
			return false;
		Block b = blocks[row][col];
		return b == null || b.isProjectilePassThrough();
	}

	public boolean handleProjectileHit(int row, int col) {
		return handleProjectileHit(row, col, 1);
	}

	public boolean handleProjectileHit(int row, int col, int damage) {
		if (!isInside(row, col))
			return false;

		Block b = blocks[row][col];

		if (b == null) {
			return true;
		}
		if (b.isProjectilePassThrough()) {
			return true;
		}

		b.takeDamage(damage);

		if (b.isDestroyed()) {
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

	public synchronized Block getBlock(int row, int col) {
		if (!isInside(row, col))
			return null;
		return blocks[row][col];
	}

	public synchronized void setBlock(int row, int col, Block block) {
		if (!isInside(row, col))
			return;

		blocks[row][col] = block;

		if (block != null && block.isBase()) {
			base = (Base) block;
		}
	}
}
