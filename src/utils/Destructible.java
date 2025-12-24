package utils;

public interface Destructible {
    // Reduz a vida do objeto
    void takeDamege(int damege);

    // Checa se o objeto esta "vivo"
    // Retorna true se vida <=0, falso se nao for
    boolean isDestroyed();

    // Logica para quando a vida chegar a zero
    void onDestroy();

}
