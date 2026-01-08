package projectiles;

public class BasicProjectile extends Projectiles {
    // Atributo adicional especifico deste tipo de projetil
    private int damage = 1;

    // CONSTRUTOR - inicializa o projetil basico
    public BasicProjectile(int startX, int startY, Direction direction) {
        // super() chama o construtor da classe pai (Projectile)
        super(startX, startY, direction);
        System.out.println("BasicProjectile criado em (" + startX + ", " + startY + ")");
    }

    // IMPLEMENTACAO do metodo abstrato move()
    @Override
    protected void move() {
        // Atualiza posicao usando a direcao
        x += direction.getDx(); // Move no eixo X
        y += direction.getDy(); // Move no eixo Y

        System.out.println("BasicProjectile em (" + x + ", " + y + ")");

        // TODO: Adicionar verificacao de colisoes depois
        // TODO: Adicionar verificacao de saida do mapa
    }

    // METODO ESPECIFICO desta classe
    public int getDamage() {
        return damage;
    }

    // METODO para testar colisao simples (vamos melhorar depois)
    public boolean checkCollision(int targetX, int targetY) {
        return this.x == targetX && this.y == targetY;
    }
}