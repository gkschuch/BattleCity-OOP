package projectiles;

import utils.Direction;
import game.InputController;
import projectiles.strategy.MoveStrategy;

public class BasicProjectile extends Projectiles {

    // Construtor original (mantém compatibilidade)
    public BasicProjectile(int startX, int startY, Direction direction, int damage, InputController input) {
        super(startX, startY, direction, damage, input);
    }

    // Construtor com Strategy personalizado
    public BasicProjectile(int startX, int startY, Direction direction, MoveStrategy strategy, InputController input) {
        super(startX, startY, direction, strategy, input);
    }

    @Override
    public int getDamage() {
        return super.getDamage();
    }

    public boolean checkCollision(int targetX, int targetY) {
        return this.getX() == targetX && this.getY() == targetY;
    }

    // Método para debug/informação
    public String getInfo() {
        return String.format("BasicProjectile[Pos:(%d,%d), Dano:%d, Estratégia:%s]",
                getX(), getY(), getDamage(), getMoveStrategy().getStrategyName());
    }
}