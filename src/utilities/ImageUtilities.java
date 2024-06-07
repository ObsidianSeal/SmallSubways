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
import java.awt.*;
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
    public static Image importImage(String path) {
        Image image = null;

        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An image failed to load: " + path, "ERROR 404 / Not Found", JOptionPane.ERROR_MESSAGE);
        }

        return image;
    }

    /**
     * Image resizer, uses the width and height of the window.
     * @param image The image to resize.
     * @return The resized image.
     */
    public static Image resizeFullScreen(Image image) {
        return image.getScaledInstance(Main.mainFrame.getWidth(), Main.mainFrame.getHeight(), Image.SCALE_SMOOTH);
    }

    /**
     * Image resizer.
     * @param image The image to resize.
     * @param width The width to resize to.
     * @param height The height to resize to.
     * @return The resized image.
     */
    public static Image rezise(Image image, int width, int height) {
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    /**
     * Draw an image fullscreen, likely for a background.
     * @param image The BufferedImage to draw fullscreen.
     */
    public static void drawImageFullScreen(Image image) {
        Main.g2D.drawImage(image, 0, 0, Main.mainFrame.getWidth(), Main.mainFrame.getHeight(), null);
    }

    /**
     * Draw an image; actual size.
     * @param image The BufferedImage to draw.
     * @param x Top left x-coordinate.
     * @param y Top left y-coordinate.
     */
    public static void drawImage(Image image, int x, int y) {
        Main.g2D.drawImage(image, x, y, null);
    }

    /**
     * Draw an image; can be resized.
     * @param image The BufferedImage to draw.
     * @param x Top left x-coordinate.
     * @param y Top left y-coordinate.
     * @param width Desired width.
     * @param height Desired height.
     */
    public static void drawImage(Image image, int x, int y, int width, int height) {
        Main.g2D.drawImage(image, x, y, width, height, null);
    }

}
