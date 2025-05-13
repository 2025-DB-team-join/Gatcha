package gotcha.common;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

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
            return new Font("SansSerif", Font.PLAIN, (int) size);  // fallback font
        }
    }
}

