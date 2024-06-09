/*
 * TITLE: Train
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Represents a train.
 */

package objects;

import main.Main;

import java.awt.*;

/**
 * Train game, now with trains.
 */
public abstract class Train {
    private MetroLine line;
    private double x1, y1, x2, y2;

    /**
     * Train constructor.
     * @param line The line that the train is to be on.
     */
    Train(MetroLine line) {
        this.line = line;

        this.x1 = line.getStations().getFirst().getX() + Main.gridSize / 2;
        this.y1 = line.getStations().getFirst().getY() + Main.gridSize / 2;

        this.x2 = line.getStations().getFirst().getX() + Main.gridSize / 2 + Main.gridSize;
        this.y2 = line.getStations().getFirst().getY() + Main.gridSize / 2;
    }

    /**
     * Move the train.
     */
    public void move() {
        this.x1++;
        this.y1++;

        this.x2++;
        this.y2++;
    }

    /**
     * Draw the train.
     */
    public void draw() {
        Main.g2D.setColor(this.line.getColour());
        Main.g2D.setStroke(new BasicStroke((float) (Main.gridSize * 0.8), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        Main.g2D.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }

}
