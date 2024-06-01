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
import java.awt.geom.GeneralPath;

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
        generateCoordinates();
        this.type = Shape.values()[(int) (Math.random() * Shape.values().length)];
        this.diagonal = false;
        this.selected = false;
        this.selectedColour = Color.WHITE;

        updateGridAvailability();
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
        this.selectedColour = Color.WHITE;

        updateGridAvailability();
    }

    /**
     * Station constructor with random coordinates only; coordinates are from the 80x45 grid and converted to pixels.
     * @param type Assign the station's type.
     */
    public Station(Shape type) {
        this.size = Main.mainFrame.getWidth() / 80.0;
        generateCoordinates();
        this.type = type;
        this.diagonal = false;
        this.selected = false;
        this.selectedColour = Color.WHITE;

        updateGridAvailability();
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
        this.selectedColour = Color.WHITE;

        updateGridAvailability();
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
        this.selectedColour = Color.WHITE;

        updateGridAvailability();
    }

    /**
     * Generate random coordinates for the station that are allowed by the grid.
     */
    private void generateCoordinates() {
        int gridX, gridY;

        do {
            gridX = (int) (Math.random() * 80);
            gridY = (int) (Math.random() * 45);
        } while (Main.grid[gridY][gridX]);

        this.x = (gridX) * (Main.mainFrame.getWidth() / 80.0);
        this.y = (gridY) * (Main.mainFrame.getHeight() / 45.0);
    }

    /**
     * Prevent future stations from spawning too close to existing ones.
     */
    private void updateGridAvailability() {
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (!(Math.abs(i) == 2 && Math.abs(j) == 2)) {
                    int gridX = (int) (this.x / this.size) + j;
                    int gridY = (int) (this.y / this.size) + i;

                    if (gridX >= 0 && gridX < 80 && gridY >= 0 && gridY < 45) Main.grid[gridY][gridX] = true;
                }
            }
        }
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

        // Shape filling; very manual. If you make any changes here, make sure to also change the corresponding draw method.
        switch (this.type) {
            case CIRCLE -> Main.g2D.fillOval(iX, iY, iSize, iSize);
            case TRIANGLE ->  {iSize = (int) (this.size * 1.2); iX -= iSize/10; iY -= iSize/10; Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize}, new int[] {iY + iSize * 15 / 16, iY + iSize / 16, iY + iSize * 15 / 16}, 3);}
            case SQUARE -> {iSize = (int) (this.size * 0.9); iX += iSize/20; iY += iSize/20; Main.g2D.fillRect(iX, iY, iSize, iSize);}
            case STAR -> {iSize = (int) (this.size*1.3); iX -= iSize*3/20; iY -= iSize*3/20; Main.g2D.fillPolygon(new int[] {iX, iX + iSize * 3 / 8, iX + iSize / 2, iX + iSize * 5 / 8, iX + iSize, iX + iSize * 11 / 16, iX + iSize * 25 / 32, iX + iSize / 2, iX + iSize * 7 / 32, iX + iSize * 5 / 16}, new int[] {iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize/16, iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize * 19 / 32, iY + iSize * 15 / 16, iY + iSize * 3 / 4, iY + iSize * 15 / 16, iY + iSize * 19 / 32}, 10);}
            case PENTAGON -> {iSize = (int) (this.size*1.1); iX -= iSize/20; iY -= iSize/20; Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 4 / 5, iX + iSize / 5}, new int[] {iY + iSize * 2 / 5, iY + iSize / 40, iY + iSize * 2 / 5, iY + iSize * 39 / 40, iY + iSize * 39 / 40}, 5);}
            case GEM -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize / 2}, new int[] {iY + iSize / 2, iY, iY + iSize / 2, iY + iSize}, 4);
            case CROSS -> Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 3, iX + iSize / 3, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize, iX + iSize, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize / 3, iX + iSize / 3, iX}, new int[] {iY + iSize / 3, iY + iSize / 3, iY, iY, iY + iSize / 3, iY + iSize / 3, iY + iSize * 2 / 3, iY + iSize * 2 / 3, iY + iSize, iY + iSize, iY + iSize * 2 / 3, iY + iSize * 2 / 3}, 12);
            case WEDGE ->  {
                iY += iSize / 5;
                GeneralPath shape = new GeneralPath();
                shape.moveTo(iX + iSize / 2f, iY - iSize / 10f);
                shape.lineTo(iX + iSize, iY + iSize / 2f);
                shape.curveTo(iX + iSize, iY + iSize / 2f, iX + iSize / 2f, iY + iSize * 6 / 5f, iX, iY + iSize / 2f);
                shape.closePath();
                Main.g2D.fill(shape);
            }
            case DIAMOND -> {iSize = (int) (this.size*1.2); iX -= iSize/10; iY -= iSize/10; Main.g2D.fillPolygon(new int[] {iX + iSize / 2, iX, iX + iSize / 5, iX + iSize * 4 / 5, iX + iSize}, new int[] {iY + iSize * 17 / 20, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize * 3 / 20, iY + iSize * 7 / 20}, 5);}
            case OVAL -> {
                GeneralPath shape = new GeneralPath();
                shape.moveTo(iX, iY + iSize);
                shape.curveTo(iX, iY + iSize, iX - iSize / 10f, iY - iSize / 10f, iX + iSize, iY);
                shape.curveTo(iX + iSize, iY, iX + iSize * 11 / 10f, iY + iSize * 11 / 10f, iX, iY + iSize);
                shape.closePath();
                Main.g2D.fill(shape);
            }
        }
    }

    /**
     * Draw a shape's outline.
     * @param strokeMultiplier The stroke size multiplier.
     */
    private void drawShape(float strokeMultiplier) {
        int iX = (int) (this.x);
        int iY = (int) (this.y);
        int iSize = (int) (this.size);

        int strokeSize;
        if (this.type == Shape.STAR) strokeSize = (int) (Main.mainFrame.getWidth() / 480f * strokeMultiplier);
        else strokeSize = (int) (Main.mainFrame.getWidth() / 384f * strokeMultiplier);

        if (this.type == Shape.OVAL) Main.g2D.setStroke(new BasicStroke(strokeSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        else Main.g2D.setStroke(new BasicStroke(strokeSize));

        // Shape drawing; very manual. If you make any changes here, make sure to also change the corresponding fill method.
        switch (this.type) {
            case CIRCLE -> Main.g2D.drawOval(iX, iY, iSize, iSize);
            case TRIANGLE -> {iSize = (int) (this.size*1.2); iX -= iSize/10; iY -= iSize/10; Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize}, new int[] {iY + iSize * 15 / 16, iY + iSize / 16, iY + iSize * 15 / 16}, 3);}
            case SQUARE -> {iSize = (int) (this.size * 0.9); iX += iSize/20; iY += iSize/20; Main.g2D.drawRect(iX, iY, iSize, iSize);}
            case STAR -> {iSize = (int) (this.size*1.3); iX -= iSize*3/20; iY -= iSize*3/20; Main.g2D.drawPolygon(new int[] {iX, iX + iSize * 3 / 8, iX + iSize / 2, iX + iSize * 5 / 8, iX + iSize, iX + iSize * 11 / 16, iX + iSize * 25 / 32, iX + iSize / 2, iX + iSize * 7 / 32, iX + iSize * 5 / 16}, new int[] {iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize/16, iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize * 19 / 32, iY + iSize * 15 / 16, iY + iSize * 3 / 4, iY + iSize * 15 / 16, iY + iSize * 19 / 32}, 10);}
            case PENTAGON -> {iSize = (int) (this.size*1.1); iX -= iSize/20; iY -= iSize/20; Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 4 / 5, iX + iSize / 5}, new int[] {iY + iSize * 2 / 5, iY + iSize / 40, iY + iSize * 2 / 5, iY + iSize * 39 / 40, iY + iSize * 39 / 40}, 5);}
            case GEM -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize / 2}, new int[] {iY + iSize / 2, iY, iY + iSize / 2, iY + iSize}, 4);
            case CROSS -> Main.g2D.drawPolygon(new int[] {iX, iX + iSize / 3, iX + iSize / 3, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize, iX + iSize, iX + iSize * 2 / 3, iX + iSize * 2 / 3, iX + iSize / 3, iX + iSize / 3, iX}, new int[] {iY + iSize / 3, iY + iSize / 3, iY, iY, iY + iSize / 3, iY + iSize / 3, iY + iSize * 2 / 3, iY + iSize * 2 / 3, iY + iSize, iY + iSize, iY + iSize * 2 / 3, iY + iSize * 2 / 3}, 12);
            case WEDGE ->  {
                iY += iSize / 5;
                GeneralPath shape = new GeneralPath();
                shape.moveTo(iX + iSize / 2f, iY - iSize / 10f);
                shape.lineTo(iX + iSize, iY + iSize / 2f);
                shape.curveTo(iX + iSize, iY + iSize / 2f, iX + iSize / 2f, iY + iSize * 6 / 5f, iX, iY + iSize / 2f);
                shape.closePath();
                Main.g2D.draw(shape);
            }
            case DIAMOND -> {iSize = (int) (this.size*1.2); iX -= iSize/10; iY -= iSize/10; Main.g2D.drawPolygon(new int[] {iX + iSize / 2, iX, iX + iSize / 5, iX + iSize * 4 / 5, iX + iSize}, new int[] {iY + iSize * 17 / 20, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize * 3 / 20, iY + iSize * 7 / 20}, 5);}
            case OVAL -> {
                GeneralPath shape = new GeneralPath();
                shape.moveTo(iX, iY + iSize);
                shape.curveTo(iX, iY + iSize, iX - iSize / 10f, iY - iSize / 10f, iX + iSize, iY);
                shape.curveTo(iX + iSize, iY, iX + iSize * 11 / 10f, iY + iSize * 11 / 10f, iX, iY + iSize);
                shape.closePath();
                Main.g2D.draw(shape);
            }
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
