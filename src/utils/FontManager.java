package utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontManager {
    private static Font customFont;

    public static void load() {
        try {
            File fontFile = new File("resources/Minecraftia.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (IOException | FontFormatException e) {
            customFont = new Font("Monospaced", Font.BOLD, 14);
        }
    }

    public static Font getFont(float size) {
        if (customFont == null) {
            load();
        }
        return customFont.deriveFont(size);
    }
}
