/*
 * TITLE: Station
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Object; represents a subway station.
 */

package objects;

import enums.Shape;
import main.Main;

import java.awt.*;

/**
 * The Station object; double values because of different screen sizes.
 */
public class Station {
    private double size;
    private double x, y;
    private Shape type;

    /**
     * Station constructor; coordinates are from the 64x36 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     * @param type Assign the station's type.
     */
    public Station(int x, int y, Shape type) {
        this.size = Main.mainFrame.getWidth() / 64.0;
        this.x = x * (Main.mainFrame.getWidth() / 64.0);
        this.y = y * (Main.mainFrame.getHeight() / 36.0);
        this.type = type;
    }

    /**
     * Get the station's size.
     * @return The station's current size.
     */
    public double getSize() {
        return this.size;
    }

    /**
     * Set the station's size.
     * @param size The station's new size.
     */
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * Get the station's x-coordinate.
     * @return The station's current x-coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Set the station's x-coordinate.
     * @param x The station's new x-coordinate.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Get the station's y-coordinate.
     * @return The station's current y-coordinate.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set the station's y-coordinate.
     * @param y The station's new y-coordinate.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Get the station's type.
     * @return The station's current type.
     */
    public Shape getType() {
        return type;
    }

    /**
     * Set the station's type.
     * @param type The station's new type.
     */
    public void setType(Shape type) {
        this.type = type;
    }

    /**
     * Draw the station.
     */
    public void draw() {
        Main.g2D.setColor(Color.WHITE);
        switch (this.type) {
            case CIRCLE -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case TRIANGLE -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case SQUARE -> Main.g2D.fillRect((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case STAR -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case PENTAGON -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case GEM -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case CROSS -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case WEDGE -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case DIAMOND -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case OVAL -> Main.g2D.fillOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
        }

        Main.g2D.setColor(Color.BLACK);
        Main.g2D.setStroke(new BasicStroke(3));
        switch (this.type) {
            case CIRCLE -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case TRIANGLE -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case SQUARE -> Main.g2D.drawRect((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case STAR -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case PENTAGON -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case GEM -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case CROSS -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case WEDGE -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case DIAMOND -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
            case OVAL -> Main.g2D.drawOval((int) this.x, (int) this.y, (int) this.size, (int) this.size);
        }
    }
}
