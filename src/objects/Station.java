/*
 * TITLE: Station
 * AUTHOR: Benjamin Gosselin, Daniel Zhong
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
    private boolean diagonal;
    private boolean selected;
    private Color selectedColour;

    /**
     * Random station generator.
     */
    public Station() {
        this.size = Main.mainFrame.getWidth() / 80.0;
        this.x = (int) (Math.random() * 80) * (Main.mainFrame.getWidth() / 80.0);
        this.y = (int) (Math.random() * 45) * (Main.mainFrame.getHeight() / 45.0);
        this.type = Shape.values()[(int) (Math.random() * Shape.values().length)];
        this.diagonal = false;
        this.selected = false;
    }

    /**
     * Station constructor with random type only; coordinates are from the 80x45 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     */
    public Station(int x, int y) {
        this.size = Main.mainFrame.getWidth() / 80.0;
        this.x = x * (Main.mainFrame.getWidth() / 80.0);
        this.y = y * (Main.mainFrame.getHeight() / 45.0);
        this.type = Shape.values()[(int) (Math.random() * Shape.values().length)];
        this.diagonal = false;
        this.selected = false;
    }

    /**
     * Station constructor with random coordinates only; coordinates are from the 80x45 grid and converted to pixels.
     * @param type Assign the station's type.
     */
    public Station(Shape type) {
        this.size = Main.mainFrame.getWidth() / 80.0;
        this.x = (int) (Math.random() * 80) * (Main.mainFrame.getWidth() / 80.0);
        this.y = (int) (Math.random() * 45) * (Main.mainFrame.getHeight() / 45.0);
        this.type = type;
        this.diagonal = false;
        this.selected = false;
    }

    /**
     * Station constructor; coordinates are from the 80x45 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     * @param type Assign the station's type.
     */
    public Station(int x, int y, Shape type) {
        this.size = Main.mainFrame.getWidth() / 80.0;
        this.x = x * (Main.mainFrame.getWidth() / 80.0);
        this.y = y * (Main.mainFrame.getHeight() / 45.0);
        this.type = type;
        this.diagonal = false;
        this.selected = false;
    }

    /**
     * Station constructor with diagonal control; coordinates are from the 80x45 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     * @param type Assign the station's type.
     * @param diagonal Assign the station's line connection mode.
     */
    public Station(int x, int y, Shape type, boolean diagonal) {
        this.size = Main.mainFrame.getWidth() / 80.0;
        this.x = x * (Main.mainFrame.getWidth() / 80.0);
        this.y = y * (Main.mainFrame.getHeight() / 45.0);
        this.type = type;
        this.diagonal = diagonal;
        this.selected = false;
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
     * Get whether the station is should be drawn to diagonally first.
     * @return Whether the station is should be drawn to diagonally first or not.
     */
    public boolean isDiagonal() {
        return this.diagonal;
    }

    /**
     * Set whether the station is should be drawn to diagonally first.
     * @param diagonal The diagonal state of the station.
     */
    public void setDiagonal(boolean diagonal) {
        this.diagonal = diagonal;
    }

    /**
     * Get whether the station is selected.
     * @return Whether the station is selected or not.
     */
    public boolean isSelected() {
        return this.selected;
    }

    /**
     * Set whether the station is selected.
     * @param selected The selection state of the station.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Get the colour of the station select highlight.
     * @return The current station select colour.
     */
    public Color getSelectedColour() {
        return this.selectedColour;
    }

    /**
     * Set the colour of the station select highlight.
     * @param selectedColour The new station select colour.
     */
    public void setSelectedColour(Color selectedColour) {
        this.selectedColour = selectedColour;
    }

    /**
     * Draw the station.
     */
    public void draw() {
        Main.g2D.setColor(Color.WHITE);
        fillShape();

        Main.g2D.setColor(Color.BLACK);
        drawShape(1);
    }

    /**
     * Fill a shape's background.
     */
    private void fillShape() {
        int iX = (int) (this.x);
        int iY = (int) (this.y);
        int iSize = (int) (this.size);

        switch (this.type) {
            case CIRCLE -> Main.g2D.fillOval(iX, iY, iSize, iSize);
            case TRIANGLE -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize}, new int[] {iY + iSize * 15 / 16, iY + iSize / 16, iY + iSize * 15 / 16}, 3);
            case SQUARE -> Main.g2D.fillRect(iX, iY, iSize, iSize);
            case STAR -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize * 3 / 8, iX + iSize / 2, iX + iSize * 5 / 8, iX + iSize, iX + iSize * 11 / 16, iX + iSize * 13 / 16, iX + iSize / 2, iX + iSize * 3 / 16, iX + iSize * 5 / 16}, new int[] {iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize / 16, iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize * 9 / 16, iY + iSize * 15 / 16, iY + iSize * 3 / 4, iY + iSize * 15 / 16, iY + iSize * 9 / 16}, 10);
            case PENTAGON -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 4 / 5, iX + iSize / 5}, new int[] {iY + iSize * 2 / 5, iY + iSize / 40, iY + iSize * 2 / 5, iY + iSize * 39 / 40, iY + iSize * 39 / 40}, 5);
            case GEM -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize / 2}, new int[] {iY + iSize / 2, iY, iY + iSize / 2, iY + iSize}, 4);
            case CROSS -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 3, iX + iSize / 3, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize, iX + iSize, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize / 3, iX + iSize / 3, iX}, new int[] {iY + iSize / 3, iY + iSize / 3, iY, iY, iY + iSize / 3, iY + iSize / 3, iY + iSize * 2 / 3, iY + iSize * 2 / 3, iY + iSize, iY + iSize, iY + iSize * 2 / 3, iY + iSize * 2 / 3}, 12);
            case WEDGE -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 17 / 20, iX + iSize * 7 / 10, iX + iSize / 2, iX + iSize * 3 / 10, iX + iSize * 3 / 20}, new int[] {iY + iSize * 3 / 5, iY + iSize / 10, iY + iSize * 3 / 5, iY + iSize * 3 / 5, iY + iSize * 17 / 20, iY + iSize * 9 / 10, iY + iSize * 17 / 20, iY + iSize * 3 / 5}, 8);
            case DIAMOND -> Main.g2D.fillPolygon(new int[] {iX + iSize / 2, iX, iX + iSize / 5, iX + iSize * 4 / 5, iX + iSize}, new int[] {iY + iSize * 17 / 20, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize * 3 / 20, iY + iSize * 7 / 20}, 5);
            case OVAL -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 20, iX + iSize * 3 / 20, iX + iSize * 7 / 20, iX + iSize * 3 / 5, iX + iSize * 4 / 5, iX + iSize, iX + iSize * 19 / 20, iX + iSize * 17 / 20, iX + iSize * 13 / 20, iX + iSize * 2 / 5, iX + iSize / 5}, new int[] {iY + iSize, iY + iSize * 4 / 5, iY + iSize * 3 / 5, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize / 20, iY, iY + iSize / 5, iY + iSize * 2 / 5, iY + iSize * 13 / 20, iY + iSize * 17 / 20, iY + iSize * 19 / 20}, 12);
        }
    }

    /**
     * Draw a shape's outline.
     * @param strokeSize The stroke size multiplier.
     */
    private void drawShape(float strokeSize) {
        int iX = (int) (this.x);
        int iY = (int) (this.y);
        int iSize = (int) (this.size);

        Main.g2D.setStroke(new BasicStroke(Main.mainFrame.getWidth() / 384f * strokeSize));

        switch (this.type) {
            case CIRCLE -> Main.g2D.drawOval(iX, iY, iSize, iSize);
            case TRIANGLE -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize}, new int[] {iY + iSize * 15 / 16, iY + iSize / 16, iY + iSize * 15 / 16}, 3);
            case SQUARE -> Main.g2D.drawRect(iX, iY, iSize, iSize);
            case STAR -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize * 3 / 8, iX + iSize / 2, iX + iSize * 5 / 8, iX + iSize, iX + iSize * 11 / 16, iX + iSize * 13 / 16, iX + iSize / 2, iX + iSize * 3 / 16, iX + iSize * 5 / 16}, new int[] {iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize / 16, iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize * 9 / 16, iY + iSize * 15 / 16, iY + iSize * 3 / 4, iY + iSize * 15 / 16, iY + iSize * 9 / 16}, 10);
            case PENTAGON -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 4 / 5, iX + iSize / 5}, new int[] {iY + iSize * 2 / 5, iY + iSize / 40, iY + iSize * 2 / 5, iY + iSize * 39 / 40, iY + iSize * 39 / 40}, 5);
            case GEM -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize / 2}, new int[] {iY + iSize / 2, iY, iY + iSize / 2, iY + iSize}, 4);
            case CROSS -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 3, iX + iSize / 3, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize, iX + iSize, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize / 3, iX + iSize / 3, iX}, new int[] {iY + iSize / 3, iY + iSize / 3, iY, iY, iY + iSize / 3, iY + iSize / 3, iY + iSize * 2 / 3, iY + iSize * 2 / 3, iY + iSize, iY + iSize, iY + iSize * 2 / 3, iY + iSize * 2 / 3}, 12);
            case WEDGE -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 17 / 20, iX + iSize * 7 / 10, iX + iSize / 2, iX + iSize * 3 / 10, iX + iSize * 3 / 20}, new int[] {iY + iSize * 3 / 5, iY + iSize / 10, iY + iSize * 3 / 5, iY + iSize * 3 / 4, iY + iSize * 17 / 20, iY + iSize * 9 / 10, iY + iSize * 17 / 20, iY + iSize * 3 / 4}, 8);
            case DIAMOND -> Main.g2D.drawPolygon(new int[] {iX + iSize / 2, iX, iX + iSize / 5, iX + iSize * 4 / 5, iX + iSize}, new int[] {iY + iSize * 17 / 20, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize * 3 / 20, iY + iSize * 7 / 20}, 5);
            case OVAL -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 20, iX + iSize * 3 / 20, iX + iSize * 7 / 20, iX + iSize * 3 / 5, iX + iSize * 4 / 5, iX + iSize, iX + iSize * 19 / 20, iX + iSize * 17 / 20, iX + iSize * 13 / 20, iX + iSize * 2 / 5, iX + iSize / 5}, new int[] {iY + iSize, iY + iSize * 4 / 5, iY + iSize * 3 / 5, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize / 20, iY, iY + iSize / 5, iY + iSize * 2 / 5, iY + iSize * 13 / 20, iY + iSize * 17 / 20, iY + iSize * 19 / 20}, 12);
        }
    }

    /**
     * Show that the station is selected, currently only one shape and colour.
     */
    public void highlight() {
        Main.g2D.setColor(this.selectedColour);
        drawShape(3);
    }

}
