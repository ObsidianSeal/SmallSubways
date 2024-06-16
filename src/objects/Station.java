/*
 * TITLE: Station
 * AUTHOR: Benjamin Gosselin, Daniel Zhong
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Subway station; 10 different shapes.
 */

package objects;

import enums.Shape;
import main.Main;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Station object; double values because of different screen sizes.
 */
public class Station {
    private double size;
    private boolean selected;
    private double x, y;
    private Shape type;
    private HashMap<MetroLine, Boolean> diagonal;
    private ArrayList<Passenger> passengers;

    /**
     * Random station generator.
     */
    public Station() {
        this.size = Main.gridSize;
        generateCoordinates();
        this.type = generateType();
        this.diagonal = new HashMap<MetroLine, Boolean>();
        this.selected = false;
        this.passengers = new ArrayList<Passenger>();

        updateGridAvailability();
        updateShapeAvailability();
    }

    /**
     * Station constructor with random type only; coordinates are from the 80x45 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     */
    public Station(int x, int y) {
        this.size = Main.gridSize;
        this.x = x * (Main.gridSize);
        this.y = y * Main.gridSize;
        this.type = generateType();
        this.diagonal = new HashMap<MetroLine, Boolean>();
        this.selected = false;
        this.passengers = new ArrayList<Passenger>();

        updateGridAvailability();
        updateShapeAvailability();
    }

    /**
     * Station constructor with random coordinates only; coordinates are from the 80x45 grid and converted to pixels.
     * @param type Assign the station's type.
     */
    public Station(Shape type) {
        this.size = Main.gridSize;
        generateCoordinates();
        this.type = type;
        this.diagonal = new HashMap<MetroLine, Boolean>();
        this.selected = false;
        this.passengers = new ArrayList<Passenger>();

        updateGridAvailability();
        updateShapeAvailability();
    }

    /**
     * Station constructor; coordinates are from the 80x45 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     * @param type Assign the station's type.
     */
    public Station(int x, int y, Shape type) {
        this.size = Main.gridSize;
        this.x = x * (Main.gridSize);
        this.y = y * Main.gridSize;
        this.type = type;
        this.diagonal = new HashMap<MetroLine, Boolean>();
        this.selected = false;
        this.passengers = new ArrayList<Passenger>();

        updateGridAvailability();
        updateShapeAvailability();
    }

    /**
     * Station constructor with diagonal control; coordinates are from the 80x45 grid and converted to pixels.
     * @param x Assign the station's x-coordinate.
     * @param y Assign the station's y-coordinate.
     * @param type Assign the station's type.
     * @param diagonal Assign the station's line connection mode.
     */
    public Station(int x, int y, Shape type, boolean diagonal) {
        this.size = Main.gridSize;
        this.x = x * (Main.gridSize);
        this.y = y * Main.gridSize;
        this.type = type;
        this.diagonal = new HashMap<MetroLine, Boolean>();
        this.selected = false;
        this.passengers = new ArrayList<Passenger>();

        updateGridAvailability();
        updateShapeAvailability();
    }

    /**
     * Generate a random station type from percentage chances.
     * @return The random station type.
     */
    public Shape generateType() {
        int r = (int) (Math.random() * 100);

        if (r < 2) return Shape.OVAL; // 2%
        else if (r < 4) return Shape.GEM; // 2%
        else if (r < 6) return Shape.STAR; // 2%
        else if (r < 8) return Shape.CROSS; // 2%
        else if (r < 10) return Shape.DIAMOND; // 2%
        else if (r < 12) return Shape.PENTAGON; // 2%
        else if (r < 14) return Shape.WEDGE; // 2%
        else if (r < 25) return Shape.SQUARE; // 11%
        else if (r < 50) return Shape.TRIANGLE; // 25%
        else return Shape.CIRCLE; // 50%
    }

    /**
     * Generate random coordinates for the station that are allowed by the grid.
     */
    private void generateCoordinates() {
        int gridX, gridY;

        do {
            // generate random coordinates
            gridX = (int) (Math.random() * 80);
            gridY = (int) (Math.random() * 45);
            // regenerate if the coordinate is illegal or if the coordinate is COUNTRY and unlucky (1 in 10 chance)
        } while ((Main.grid[gridY][gridX] != Main.COUNTRY && Main.grid[gridY][gridX] <= Main.WATER) || (Main.grid[gridY][gridX] == Main.COUNTRY && (int) (Math.random() * 10) != 0));

        // convert to pixels
        this.x = (gridX) * (Main.gridSize);
        this.y = (gridY) * Main.gridSize;
    }

    /**
     * Prevent future stations from spawning too close to existing ones.
     */
    private void updateGridAvailability() {
        // buffer zone around stations
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                if (!(Math.abs(i) == 3 && Math.abs(j) == 3 || Math.abs(i) == 3 && Math.abs(j) == 2 || Math.abs(i) == 2 && Math.abs(j) == 3)) {
                    int gridX = (int) (this.x / this.size) + j;
                    int gridY = (int) (this.y / this.size) + i;

                    if (gridX >= 0 && gridX < 80 && gridY >= 0 && gridY < 45) Main.grid[gridY][gridX] = Main.TAKEN;
                }
            }
        }

        // update the number of available squares
        int openCount = 0;
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 80; j++) {
                if (Main.grid[i][j] > Main.WATER) openCount++;
            }
        }
        Main.openCount = openCount;
    }

    /**
     * Update list of shapes present on the map.
     */
    public void updateShapeAvailability() {
        if (!Main.shapesPresent.contains(this.type)) Main.shapesPresent.add(this.type);
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
    public boolean isDiagonal(MetroLine line) {
        return this.diagonal.get(line);
    }

    /**
     * Set whether the station is should be drawn to diagonally first.
     * @param diagonal The diagonal state of the station.
     */
    public void setDiagonal(MetroLine line, boolean diagonal) {
        this.diagonal.put(line, diagonal);
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
     * Draw the station, default colour.
     */
    public void draw() {
        draw(Color.BLACK);
    }

    /**
     * Draw the station, specified colour.
     */
    public void draw(Color outlineColour) {
        Main.g2D.setColor(Color.WHITE);
        fillShape(0, 0, 1, this.type);

        Main.g2D.setColor(outlineColour);
        drawShape(1);
    }

    /**
     * Fill a shape's background.
     * @param offsetX How far from the station's x-coordinate the shape should be drawn.
     * @param offsetY How far from the station's y-coordinate the shape should be drawn.
     * @param sizeMultiplier How large the shape should be drawn relative to the size of a grid square.
     * @param type The shape to draw.
     */
    private void fillShape(double offsetX, double offsetY, double sizeMultiplier, Shape type) {
        int iX = (int) (this.x + offsetX);
        int iY = (int) (this.y + offsetY);
        int iSize = (int) (this.size * sizeMultiplier);

        // Shape filling; very manual. If you make any changes here, make sure to also change the corresponding draw method.
        switch (type) {
            case CIRCLE -> Main.g2D.fillOval(iX, iY, iSize, iSize);
            case TRIANGLE ->  {iSize = (int) (this.size * sizeMultiplier * 1.2); iX -= iSize/10; iY -= iSize/10; Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize}, new int[] {iY + iSize * 15 / 16, iY + iSize / 16, iY + iSize * 15 / 16}, 3);}
            case SQUARE -> {iSize = (int) (this.size * sizeMultiplier * 0.9); iX += iSize/20; iY += iSize/20; Main.g2D.fillRect(iX, iY, iSize, iSize);}
            case STAR -> {iSize = (int) (this.size * sizeMultiplier*1.3); iX -= iSize*3/20; iY -= iSize*3/20; Main.g2D.fillPolygon(new int[] {iX, iX + iSize * 3 / 8, iX + iSize / 2, iX + iSize * 5 / 8, iX + iSize, iX + iSize * 11 / 16, iX + iSize * 25 / 32, iX + iSize / 2, iX + iSize * 7 / 32, iX + iSize * 5 / 16}, new int[] {iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize/16, iY + iSize * 3 / 8, iY + iSize * 3 / 8, iY + iSize * 19 / 32, iY + iSize * 15 / 16, iY + iSize * 3 / 4, iY + iSize * 15 / 16, iY + iSize * 19 / 32}, 10);}
            case PENTAGON -> {iSize = (int) (this.size * sizeMultiplier*1.1); iX -= iSize/20; iY -= iSize/20; Main.g2D.fillPolygon(new int[] {iX, iX + iSize / 2, iX + iSize, iX + iSize * 4 / 5, iX + iSize / 5}, new int[] {iY + iSize * 2 / 5, iY + iSize / 40, iY + iSize * 2 / 5, iY + iSize * 39 / 40, iY + iSize * 39 / 40}, 5);}
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
            case DIAMOND -> {iSize = (int) (this.size * sizeMultiplier*1.2); iX -= iSize/10; iY -= iSize/10; Main.g2D.fillPolygon(new int[] {iX + iSize / 2, iX, iX + iSize / 5, iX + iSize * 4 / 5, iX + iSize}, new int[] {iY + iSize * 17 / 20, iY + iSize * 7 / 20, iY + iSize * 3 / 20, iY + iSize * 3 / 20, iY + iSize * 7 / 20}, 5);}
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

        // stroke multiplier for hover
        int strokeSize;
        if (this.type == Shape.STAR) strokeSize = (int) (Main.mainFrame.getWidth() / 480f * strokeMultiplier);
        else strokeSize = (int) (Main.mainFrame.getWidth() / 384f * strokeMultiplier);

        // different stroke for OVAL
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
    public void highlight(Color colour) {
        Main.g2D.setColor(colour);
        drawShape(3);
    }

    /**
     * Get the list of passengers.
     * @return The current list of passengers.
     */
    public ArrayList<Passenger> getPassengers() {
        return this.passengers;
    }

    /**
     * Draw the waiting passengers.
     */
    public void drawPassengers() {
        double offsetX = this.size * 1.5, offsetY = 0; // distance from top left of station
        int total = 0, row = 0; // total passengers overall and in a row
        int opacity = 255; // opacity begins at 100%

        for (Passenger passenger : this.passengers) {
            if (total >= 4 && opacity >= 30) opacity -= 15; // opacity begins decreasing at the 5th passenger

            // set colour with opacity, draw shape (same as station but smaller)
            Main.g2D.setColor(new Color(0, 0, 0, opacity));
            fillShape(offsetX, offsetY, 0.6, passenger.getType());

            // update offsets & counts
            if (row >= 3) {
                offsetY += this.size * 0.75;

                offsetX = this.size * 1.5;
                row = 0;
            } else {
                offsetX += this.size * 0.75;
                row++;
            }

            total++;
        }
    }

    /**
     * Get the grid x-coordinate of the station.
     * @return The station's x-coordinate on the grid.
     */
    public int getGridX() {
        return (int) (this.x / Main.gridSize);
    }

    /**
     * Get the grid y-coordinate of the station.
     * @return The station's y-coordinate on the grid.
     */
    public int getGridY() {
        return (int) (this.y / Main.gridSize);
    }

    /**
     * Determine if the station is connected to any lines.
     * @return If there is at least one line that connects to this station.
     */
    public boolean isConnected() {
        return !this.diagonal.isEmpty();
    }

    /**
     * Delete the record of a line's diagonal state for this station.
     * @param line The line to disconnect from.
     */
    public void disconnect(MetroLine line) {
        this.diagonal.remove(line);
    }

}
