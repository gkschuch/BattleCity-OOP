package projectiles;

public abstract class Projectiles implements Runnable {
    // ATRIBUTOS (dados que todo projetil tem);
    protected int x;            // Posição X na grade
    protected int y;            // Posição Y na grade
    protected Direction direction; // Para onde está indo
    protected boolean active = true; // Se ainda está ativo
    protected Thread thread;      // Thread para movimento

    // CONSTRUTOR (inicializa o projetil)
    public Projectiles(int startX, int startY, Direction direction) {
        this.x = startX;
        this.y = startY;
        this.direction = direction;
    }

    // MÉTODO para iniciar a thread
    public void start() {
        thread = new Thread(this); // 'this' significa ESTE objeto
        thread.start(); // Inicia a thread
    }

    // MÉTODO que a thread executa
    @Override
    public void run() {
        System.out.println("Projétil iniciado em (" + x + ", " + y + ")");

        while (active) {
            move(); // Move o projetil

            try {
                // Pausa a thread para controlar velocidade
                Thread.sleep(200); // 200ms = 5 movimentos por segundo
            } catch (InterruptedException e) {
                System.out.println("Projetil interrompido");
                break;
            }
        }
        System.out.println("Projetil finalizado");
    }

    // MÉTODO ABSTRATO (seus filhos vão implementar)
    protected abstract void move();

    // MÉTODO para desativar o projétil
    public void deactivate() {
        active = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    // GETTERS (permitem outros acessarem os dados)
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActive() { return active; }
    public Direction getDirection() { return direction; }
}