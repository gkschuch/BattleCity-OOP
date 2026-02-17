package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteManager {
    private static BufferedImage spriteSheet;

    public static void load() {
        try {
            spriteSheet = ImageIO.read(new File("resources/spriteSheet.png"));
            System.out.println("SpriteSheet carregado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao carregar a imagem sprites.png!");
            e.printStackTrace();
        }
    }

    public static BufferedImage getSprite(int x, int y, int width, int height) {
        if (spriteSheet == null) {
            load();
        }
        return spriteSheet.getSubimage(x, y, width, height);
    }

}
