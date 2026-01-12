package utils;

public interface Destructible {

	// Metodo para aplicar dano
	void takeDamage(int damage);

	// metodo para checar se esta destruido(sem vidas)
	boolean isDestroyed();

	// Metodo quando o objeto for destruido
	void onDestroy();

}
