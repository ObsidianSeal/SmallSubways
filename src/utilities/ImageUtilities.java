/*
 * TITLE: ImageUtilities
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Load, display, and otherwise work with images.
 */

package utilities;

import main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The image importer and manipulator class.
 */
public class ImageUtilities {

    /**
     * Image loader.
     * @param path The path to the image file, likely begins with "src\\images\\".
     * @return The specified image, loaded as a BufferedImage.
     */
    public static BufferedImage importImage(String path) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An image failed to load: " + path, "ERROR 404 / Not Found", JOptionPane.ERROR_MESSAGE);
        }

        return image;
    }

    /**
     * Draw an image fullscreen, likely for a background.
     * @param g2D The Graphics2D object, to draw the image.
     * @param panel The JPanel object, to get 100% of the width and height.
     * @param image The BufferedImage to draw fullscreen.
     */
    public static void drawImageFullScreen(BufferedImage image) {
        Main.g2D.drawImage(image, 0, 0, Main.mainFrame.getWidth(), Main.mainFrame.getHeight(), null);
    }

}
