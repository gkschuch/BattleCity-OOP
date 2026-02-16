package grid.blocks;

import characters.TankPlayer;
import characters.enemy.EnemyTank;
import grid.Grid;
import grid.exceptions.GridException;
import utils.Destructible;

public abstract class Block implements Destructible {
	protected int row;
	protected int col;
	protected int health;
	protected boolean destroyed; // diz se o bloco existe

	// construtor

	public Block(int row, int col) {
		if (row < 0 || col < 0)
			throw new GridException("Coordenadas do bloco não podem ser negativas: [" + row + ", " + col + "]");
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

	public abstract java.awt.Color getColor();

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

	public void onEnemyStep(EnemyTank enemy, Grid grid) {
		// Todos os blocos nao fazem nada
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

	public char getIcon() {
		if (this.isBase())
			return this.isDestroyed() ? 'x' : 'X';
		if (this.isWalkable())
			return this.isProjectilePassThrough() ? 'T' : '.';
		else
			return this.isProjectilePassThrough() ? '~' : '#';
	}

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
