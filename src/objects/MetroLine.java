/*
 * TITLE: MetroLine
 * AUTHOR: Daniel Zhong, Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Describes a subway line.
 */

package objects;

import main.Main;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 * An object representing a line of a subway system.
 */
public class MetroLine {
    private ArrayList<Station> stations;
    private Color colour;
    private int curveOffset;

    /**
     * MetroLine constructor.
     * @param stations List of stations on the line.
     * @param colour The colour of the line (for drawing).
     */
    public MetroLine(ArrayList<Station> stations, Color colour) {
        this.stations = stations;
        this.colour = colour;
        this.curveOffset = Main.mainFrame.getWidth() / 384;

        for (Station station : this.stations) {
            station.setSelectedColour(this.colour); // temporary?
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
     * @param station The station to add.
     * @return The new number of stations on the line.
     */
    public int addStation(Station station) {
        station.setSelectedColour(this.colour); // temporary?
        this.stations.add(station);
        return this.stations.size();
    }

    /**
     * Remove a station from the line.
     * @param station The station to remove.
     * @return The new number of stations on the line.
     */
    public int removeStation(Station station) {
        station.setSelectedColour(Color.WHITE);
        this.stations.remove(station);
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
            int newX1, newY1, newX, newY, newX2, newY2;

            if (!diagonal) {
                if (xDiff >= 0) {
                    // RIGHT
                    if (yDiff >= 0) {
                        // RIGHT-DOWN
                        if (xLonger) {
                            // RIGHT
                            newX = fromX + (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX - (curveOffset * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // DOWN
                            newX = fromX;
                            newY = fromY + (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY - (curveOffset * Math.sqrt(2)));
                        }
                        newX2 = newX + curveOffset;
                        newY2 = newY + curveOffset;
                    } else {
                        // RIGHT-UP
                        if (xLonger) {
                            // RIGHT
                            newX = fromX + (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX - (curveOffset * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // UP
                            newX = fromX;
                            newY = fromY - (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY + (curveOffset * Math.sqrt(2)));
                        }
                        newX2 = newX + curveOffset;
                        newY2 = newY - curveOffset;
                    }
                } else {
                    // LEFT
                    if (yDiff >= 0) {
                        // LEFT-DOWN
                        if (xLonger) {
                            // LEFT
                            newX = fromX - (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX + (curveOffset * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // DOWN
                            newX = fromX;
                            newY = fromY + (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY - (curveOffset * Math.sqrt(2)));
                        }
                        newX2 = newX - curveOffset;
                        newY2 = newY + curveOffset;
                    } else {
                        // LEFT-UP
                        if (xLonger) {
                            // LEFT
                            newX = fromX - (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX + (curveOffset * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // UP
                            newX = fromX;
                            newY = fromY - (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY + (curveOffset * Math.sqrt(2)));
                        }
                        newX2 = newX - curveOffset;
                        newY2 = newY - curveOffset;
                    }
                }
            } else {
                int distance = Math.min(xDiff2, yDiff2);

                if (xDiff >= 0) {
                    newX = fromX + distance;
                    newX1 = fromX + (distance - curveOffset);
                } else {
                    newX = fromX - distance;
                    newX1 = fromX - (distance - curveOffset);
                }

                if (yDiff >= 0) {
                    newY = fromY + distance;
                    newY1 = fromY + (distance - curveOffset);
                } else {
                    newY = fromY - distance;
                    newY1 = fromY - (distance - curveOffset);
                }

                if (xDiff >= 0) {
                    // RIGHT
                    if (yDiff >= 0) {
                        // RIGHT-DOWN
                        if (xLonger) {
                            // RIGHT
                            newX2 = (int) (newX + curveOffset * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // DOWN
                            newX2 = newX;
                            newY2 = (int) (newY + curveOffset * Math.sqrt(2));
                        }
                    } else {
                        // RIGHT-UP
                        if (xLonger) {
                            // RIGHT
                            newX2 = (int) (newX + curveOffset * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // UP
                            newX2 = newX;
                            newY2 = (int) (newY - curveOffset * Math.sqrt(2));
                        }
                    }
                } else {
                    // LEFT
                    if (yDiff >= 0) {
                        // LEFT-DOWN
                        if (xLonger) {
                            // LEFT
                            newX2 = (int) (newX - curveOffset * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // DOWN
                            newX2 = newX;
                            newY2 = (int) (newY + curveOffset * Math.sqrt(2));
                        }
                    } else {
                        // LEFT-UP
                        if (xLonger) {
                            // LEFT
                            newX2 = (int) (newX - curveOffset * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // UP
                            newX2 = newX;
                            newY2 = (int) (newY - curveOffset * Math.sqrt(2));
                        }
                    }
                }
            }

            int lineOffset = (int) (stations.getFirst().getSize() / 2);

            GeneralPath curve = new GeneralPath();
            curve.moveTo(newX1 + lineOffset, newY1 + lineOffset);
            curve.curveTo(newX1 + lineOffset, newY1 + lineOffset, newX + lineOffset, newY + lineOffset, newX2 + lineOffset, newY2 + lineOffset);

            Main.g2D.setStroke(new BasicStroke(Main.mainFrame.getWidth() / 240f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Main.g2D.setColor(this.colour);

            if (newX == fromX && newY == fromY || newX == toX && newY == toY) {
                Main.g2D.drawLine(fromX + lineOffset, fromY + lineOffset, newX + lineOffset, newY + lineOffset);
                Main.g2D.drawLine(newX + lineOffset, newY + lineOffset, toX + lineOffset, toY + lineOffset);
            } else {
                Main.g2D.drawLine(fromX + lineOffset, fromY + lineOffset, newX1 + lineOffset, newY1 + lineOffset);
                Main.g2D.draw(curve);
                Main.g2D.drawLine(newX2 + lineOffset, newY2 + lineOffset, toX + lineOffset, toY + lineOffset);
            }
        }
    }

}
