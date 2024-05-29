/*
 * TITLE: MetroLine
 * AUTHOR: Daniel Zhong, Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Describes a subway line.
 */

package objects;

import main.Main;

import java.awt.*;
import java.util.ArrayList;

/**
 * An object representing a line of a subway system.
 */
public class MetroLine {
    private ArrayList<Station> stations;
    private Color colour;

    /**
     * MetroLine constructor.
     * @param stations List of stations on the line.
     * @param colour The colour of the line (for drawing).
     */
    public MetroLine(ArrayList<Station> stations, Color colour) {
        this.stations = stations;
        this.colour = colour;

        for (Station station : this.stations) {
            station.setSelectedColour(this.colour);
        }
    }

    /**
     * Get the list of stations on the line
     * @return The current list of stations on the line.
     */
    public ArrayList<Station> getStations() {
        return stations;
    }

    /**
     * Set the list of stations on the line
     * @param stations The new list of stations.
     */
    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    /**
     * Add a station to the line.
     * @param station The station to add to the line
     * @return The new number of stations on the line.
     */
    public int addStation(Station station) {
        station.setSelectedColour(this.colour);
        this.stations.add(station);
        return this.stations.size();
    }

    /**
     * Get the line's colour.
     * @return The line's current colour.
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Set the line's colour
     * @param colour A new colour for the line.
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Draw the line as a coloured line, modified from TransitMapMaker.
     */
    public void draw() {
        for (int i = 1; i < this.stations.size(); i++) {
            int fromX = (int) this.stations.get(i - 1).getX();
            int fromY = (int) this.stations.get(i - 1).getY();
            int toX = (int) this.stations.get(i).getX();
            int toY = (int) this.stations.get(i).getY();
            boolean diagonal = this.stations.get(i).isDiagonal();

            int xDiff = toX - fromX;
            int yDiff = toY - fromY;
            int xDiff2 = Math.abs(toX - fromX);
            int yDiff2 = Math.abs(toY - fromY);
            boolean xLonger = xDiff2 >= yDiff2;
            int newX, newY;

            if (!diagonal) {
                if (xDiff >= 0) {
                    // RIGHT
                    if (yDiff >= 0) {
                        // RIGHT-DOWN
                        if (xLonger) {
                            newX = fromX + (xDiff2 - yDiff2);
                            newY = fromY;
                        } else {
                            newX = fromX;
                            newY = fromY + (yDiff2 - xDiff2);
                        }
                    } else {
                        // RIGHT-UP
                        if (xLonger) {
                            newX = fromX + (xDiff2 - yDiff2);
                            newY = fromY;
                        } else {
                            newX = fromX;
                            newY = fromY - (yDiff2 - xDiff2);
                        }
                    }
                } else {
                    // LEFT
                    if (yDiff >= 0) {
                        // LEFT-DOWN
                        if (xLonger) {
                            newX = fromX - (xDiff2 - yDiff2);
                            newY = fromY;
                        } else {
                            newX = fromX;
                            newY = fromY + (yDiff2 - xDiff2);
                        }
                    } else {
                        // LEFT-UP
                        if (xLonger) {
                            newX = fromX - (xDiff2 - yDiff2);
                            newY = fromY;
                        } else {
                            newX = fromX;
                            newY = fromY - (yDiff2 - xDiff2);
                        }
                    }
                }
            } else {
                int distance = Math.min(xDiff2, yDiff2);

                if (xDiff >= 0) newX = fromX + distance;
                else newX = fromX - distance;

                if (yDiff >= 0) newY = fromY + distance;
                else newY = fromY - distance;
            }

            int lineOffset = (int) (stations.getFirst().getSize() / 2);

            Main.g2D.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Main.g2D.setColor(this.colour);
            Main.g2D.drawLine(fromX + lineOffset, fromY + lineOffset, newX + lineOffset, newY + lineOffset);
            Main.g2D.drawLine(newX + lineOffset, newY + lineOffset, toX + lineOffset, toY + lineOffset);
        }
    }

}
