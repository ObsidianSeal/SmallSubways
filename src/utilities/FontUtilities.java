/*
 * TITLE: FontUtilities
 * AUTHOR: Benjamin Gosselin
 * DATE: Friday, June 7th, 2024
 * DESCRIPTION: Load fonts and perhaps do other things with them.
 */

package utilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Font-related utilities.
 */
public class FontUtilities {

    /**
     * Font loader and resizer.
     * @param path The path to the font file.
     * @param size The desired size of the font.
     * @return The imported font.
     */
    public static Font importFont(String path, float size) {
        Font font = null;

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
        } catch (FontFormatException e) {
            JOptionPane.showMessageDialog(null, "A font failed to load: " + path, "FontFormatException", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "A font failed to load: " + path, "IOException", JOptionPane.ERROR_MESSAGE);
        }

        return font;
    }

}
