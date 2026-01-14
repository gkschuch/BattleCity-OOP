package grid.blocks;

import characters.TankPlayer;
import grid.Grid;
import utils.Destructible;

public class Block implements Destructible {
	protected int row;
	protected int col;
	protected int health;
	protected boolean destroyed; // diz se o bloco existe

	// construtor

	public Block(int row, int col) {
		this.setRow(row);
		this.setCol(col);
		this.health = 1;
		this.destroyed = false;
	}

	// métodos

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

	public void onPlayerStep(TankPlayer player, Grid grid) {
		// Todos os blocos nao fazem nada, so os powerUps
	}

	public boolean isWalkable() {
		return false;
	}

	public boolean isProjectilePassThrough() {
		return false;
	}

	public boolean isBase() {
		return false;
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

}
