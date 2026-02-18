package characters.powerups;

import characters.TankPlayer;
import characters.exceptions.PowerUpException;
import grid.Grid;
import grid.blocks.Block;
import utils.Collectable;
import java.awt.image.BufferedImage;
import utils.SpriteManager;

public abstract class PowerUp extends Block implements Collectable {
    private PowerUpType powerUpType;

    private final BufferedImage image;

    public PowerUp(int x, int y, PowerUpType powerUpType) {
        super(x, y); // Chama super de Block

        if ((x < 0 || x > 13) || (y < 0 || y > 17))
            throw new PowerUpException("Coordenadas de spawn inválidas: " + x + "," + y);
        if (powerUpType == null)
            throw new PowerUpException("Tipo de PowerUp não pode ser nulo.");

        this.powerUpType = powerUpType;

        this.image = loadSpriteByType(powerUpType);
    }

    private BufferedImage loadSpriteByType(PowerUpType type) {
        return switch (type) {
            case HELMET -> SpriteManager.getSprite(864, 83, 166, 159);
            case CLOCK -> SpriteManager.getSprite(1316, 74, 160, 177);
            case SHOVEL -> SpriteManager.getSprite(1087, 66, 172, 181);
            case STAR -> SpriteManager.getSprite(643, 64, 177, 180);
            case BOMB -> SpriteManager.getSprite(1537, 73, 164, 174);
            case LIFE -> SpriteManager.getSprite(1755, 86, 158, 154);
        };
    }

    @Override
    public java.awt.Color getColor() {
        return java.awt.Color.MAGENTA;
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void onPlayerStep(TankPlayer player, Grid grid) {
        this.applyEffect(player);
        grid.setBlock(this.getRow(), this.getCol(), null);
        grid.removePowerUp(this);
    }

    @Override
    public char getIcon() {
        return '?';
    }

    @Override
    public abstract void applyEffect(TankPlayer player);

    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
}