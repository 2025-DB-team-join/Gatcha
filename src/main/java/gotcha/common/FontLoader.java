package gotcha.common;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class FontLoader {
    public static Font loadCustomFont(float size) {
        try (InputStream is = FontLoader.class.getResourceAsStream("/fonts/Pretendard-Regular.otf")) {
            if (is == null) {
                throw new IOException("Font resource not found");
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    public static void applyGlobalFont(float size) {
        Font globalFont = loadCustomFont(size);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, globalFont);
            }
        }
    }
}

