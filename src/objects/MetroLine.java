/*
 * TITLE: MetroLine
 * AUTHOR: Daniel Zhong, Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Describes a subway line.
 */

package objects;

import enums.Direction;
import main.Main;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 * An object representing a line of a subway system.
 */
public class MetroLine {
    private ArrayList<Station> stations;
    private ArrayList<Train> trains;
    private Color colour;

    public final int CURVE_OFFSET;
    public final int LINE_OFFSET;
    public final int END_OFFSET;

    /**
     * MetroLine constructor, empty stations list.
     * @param colour The colour of the line (for drawing).
     */
    public MetroLine(Color colour) {
        this.stations = new ArrayList<Station>();
        this.trains = new ArrayList<Train>();
        this.colour = colour;
        this.CURVE_OFFSET = Main.mainFrame.getWidth() / 384;
        this.LINE_OFFSET = Main.mainFrame.getWidth() / 160;
        this.END_OFFSET = this.CURVE_OFFSET * 5;
    }

    /**
     * MetroLine constructor.
     * @param stations List of stations on the line.
     * @param colour The colour of the line (for drawing).
     */
    public MetroLine(ArrayList<Station> stations, Color colour) {
        this.stations = stations;
        this.trains = new ArrayList<Train>();
        this.colour = colour;
        this.CURVE_OFFSET = Main.mainFrame.getWidth() / 384;
        this.LINE_OFFSET = Main.mainFrame.getWidth() / 160;
        this.END_OFFSET = this.CURVE_OFFSET * 5;
    }

    /**
     * Get the list of stations on the line
     * @return The current list of stations on the line.
     */
    public ArrayList<Station> getStations() {
        return this.stations;
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
     * @param end True if the station is to be added to the beginning of the line, false if the station is to be added to the end of the line.
     */
    public void addStation(Station station, boolean end) {
        if (end) this.stations.addFirst(station);
        else this.stations.add(station);

        if (this.stations.size() == 2) this.trains.add(new Locomotive(this)); // if there is at least one line segment, get a train moving across it
    }

    /**
     * Remove a station from the line.
     * @param station The station to remove.
     */
    public void removeStation(Station station) {
        this.stations.remove(station);

        if (this.stations.size() < 2) this.trains.clear(); // too few stations for train travel
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
     * Get the line's list of trains.
     * @return The list of trains on the line.
     */
    public ArrayList<Train> getTrains() {
        return this.trains;
    }

    /**
     * Draw the line as a coloured line, modified from TransitMapMaker.
     */
    public void draw() {
        // for each station (starting from the second) connect it to previous with one or two line segments
        for (int i = 1; i < this.stations.size(); i++) {
            // the two directions
            Direction firstDirection, secondDirection;

            // current station values
            int fromX = (int) this.stations.get(i - 1).getX();
            int fromY = (int) this.stations.get(i - 1).getY();
            int toX = (int) this.stations.get(i).getX();
            int toY = (int) this.stations.get(i).getY();
            boolean diagonal = this.stations.get(i).isDiagonal(this);

            // calculations
            int xDiff = toX - fromX;
            int yDiff = toY - fromY;
            int xDiff2 = Math.abs(toX - fromX);
            int yDiff2 = Math.abs(toY - fromY);
            boolean xLonger = xDiff2 >= yDiff2;
            int newX1, newY1, newX, newY, newX2, newY2;

            // determine directions and where the two line segments should meet
            if (!diagonal) {
                if (xDiff >= 0) {
                    // RIGHT
                    if (yDiff >= 0) {
                        // RIGHT-DOWN
                        if (xLonger) {
                            // RIGHT
                            firstDirection = Direction.RIGHT;
                            secondDirection = Direction.RIGHT_DOWN;

                            newX = fromX + (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX - (CURVE_OFFSET * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // DOWN
                            firstDirection = Direction.DOWN;
                            secondDirection = Direction.RIGHT_DOWN;

                            newX = fromX;
                            newY = fromY + (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY - (CURVE_OFFSET * Math.sqrt(2)));
                        }
                        newX2 = newX + this.CURVE_OFFSET;
                        newY2 = newY + this.CURVE_OFFSET;
                    } else {
                        // RIGHT-UP
                        if (xLonger) {
                            // RIGHT
                            firstDirection = Direction.RIGHT;
                            secondDirection = Direction.RIGHT_UP;

                            newX = fromX + (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX - (CURVE_OFFSET * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // UP
                            firstDirection = Direction.UP;
                            secondDirection = Direction.RIGHT_UP;

                            newX = fromX;
                            newY = fromY - (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY + (CURVE_OFFSET * Math.sqrt(2)));
                        }
                        newX2 = newX + this.CURVE_OFFSET;
                        newY2 = newY - this.CURVE_OFFSET;
                    }
                } else {
                    // LEFT
                    if (yDiff >= 0) {
                        // LEFT-DOWN
                        if (xLonger) {
                            // LEFT
                            firstDirection = Direction.LEFT;
                            secondDirection = Direction.LEFT_DOWN;

                            newX = fromX - (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX + (CURVE_OFFSET * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // DOWN
                            firstDirection = Direction.DOWN;
                            secondDirection = Direction.LEFT_DOWN;

                            newX = fromX;
                            newY = fromY + (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY - (CURVE_OFFSET * Math.sqrt(2)));
                        }
                        newX2 = newX - this.CURVE_OFFSET;
                        newY2 = newY + this.CURVE_OFFSET;
                    } else {
                        // LEFT-UP
                        if (xLonger) {
                            // LEFT
                            firstDirection = Direction.LEFT;
                            secondDirection = Direction.LEFT_UP;

                            newX = fromX - (xDiff2 - yDiff2);
                            newY = fromY;

                            newX1 = (int) (newX + (CURVE_OFFSET * Math.sqrt(2)));
                            newY1 = newY;
                        } else {
                            // UP
                            firstDirection = Direction.UP;
                            secondDirection = Direction.LEFT_UP;

                            newX = fromX;
                            newY = fromY - (yDiff2 - xDiff2);

                            newX1 = newX;
                            newY1 = (int) (newY + (CURVE_OFFSET * Math.sqrt(2)));
                        }
                        newX2 = newX - this.CURVE_OFFSET;
                        newY2 = newY - this.CURVE_OFFSET;
                    }
                }
            } else {
                int distance = Math.min(xDiff2, yDiff2);

                if (xDiff >= 0) {
                    newX = fromX + distance;
                    newX1 = fromX + (distance - this.CURVE_OFFSET);
                } else {
                    newX = fromX - distance;
                    newX1 = fromX - (distance - this.CURVE_OFFSET);
                }

                if (yDiff >= 0) {
                    newY = fromY + distance;
                    newY1 = fromY + (distance - this.CURVE_OFFSET);
                } else {
                    newY = fromY - distance;
                    newY1 = fromY - (distance - this.CURVE_OFFSET);
                }

                if (xDiff >= 0) {
                    // RIGHT
                    if (yDiff >= 0) {
                        // RIGHT-DOWN
                        if (xLonger) {
                            // RIGHT
                            firstDirection = Direction.RIGHT_DOWN;
                            secondDirection = Direction.RIGHT;

                            newX2 = (int) (newX + this.CURVE_OFFSET * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // DOWN
                            firstDirection = Direction.RIGHT_DOWN;
                            secondDirection = Direction.DOWN;

                            newX2 = newX;
                            newY2 = (int) (newY + this.CURVE_OFFSET * Math.sqrt(2));
                        }
                    } else {
                        // RIGHT-UP
                        if (xLonger) {
                            // RIGHT
                            firstDirection = Direction.RIGHT_UP;
                            secondDirection = Direction.RIGHT;

                            newX2 = (int) (newX + this.CURVE_OFFSET * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // UP
                            firstDirection = Direction.RIGHT_UP;
                            secondDirection = Direction.UP;

                            newX2 = newX;
                            newY2 = (int) (newY - this.CURVE_OFFSET * Math.sqrt(2));
                        }
                    }
                } else {
                    // LEFT
                    if (yDiff >= 0) {
                        // LEFT-DOWN
                        if (xLonger) {
                            // LEFT
                            firstDirection = Direction.LEFT_DOWN;
                            secondDirection = Direction.LEFT;

                            newX2 = (int) (newX - this.CURVE_OFFSET * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // DOWN
                            firstDirection = Direction.LEFT_DOWN;
                            secondDirection = Direction.DOWN;

                            newX2 = newX;
                            newY2 = (int) (newY + this.CURVE_OFFSET * Math.sqrt(2));
                        }
                    } else {
                        // LEFT-UP
                        if (xLonger) {
                            // LEFT
                            firstDirection = Direction.LEFT_UP;
                            secondDirection = Direction.LEFT;

                            newX2 = (int) (newX - this.CURVE_OFFSET * Math.sqrt(2));
                            newY2 = newY;
                        } else {
                            // UP
                            firstDirection = Direction.LEFT_UP;
                            secondDirection = Direction.UP;

                            newX2 = newX;
                            newY2 = (int) (newY - this.CURVE_OFFSET * Math.sqrt(2));
                        }
                    }
                }
            }

            // DEBUG: show directions
//            switch (firstDirection) {
//                case UP -> Main.g2D.drawString("UP", toX + 20, toY - 20);
//                case DOWN -> Main.g2D.drawString("DOWN", toX + 20, toY - 20);
//
//                case LEFT_UP -> Main.g2D.drawString("LEFT_UP", toX + 20, toY - 20);
//                case LEFT -> Main.g2D.drawString("LEFT", toX + 20, toY - 20);
//                case LEFT_DOWN -> Main.g2D.drawString("LEFT_DOWN", toX + 20, toY - 20);
//
//                case RIGHT_UP -> Main.g2D.drawString("RIGHT_UP", toX + 20, toY - 20);
//                case RIGHT -> Main.g2D.drawString("RIGHT", toX + 20, toY - 20);
//                case RIGHT_DOWN -> Main.g2D.drawString("RIGHT_DOWN", toX + 20, toY - 20);
//            }
//            switch (secondDirection) {
//                case UP -> Main.g2D.drawString("UP", toX + 20, toY - 10);
//                case DOWN -> Main.g2D.drawString("DOWN", toX + 20, toY - 10);
//
//                case LEFT_UP -> Main.g2D.drawString("LEFT_UP", toX + 20, toY - 10);
//                case LEFT -> Main.g2D.drawString("LEFT", toX + 20, toY - 10);
//                case LEFT_DOWN -> Main.g2D.drawString("LEFT_DOWN", toX + 20, toY - 10);
//
//                case RIGHT_UP -> Main.g2D.drawString("RIGHT_UP", toX + 20, toY - 10);
//                case RIGHT -> Main.g2D.drawString("RIGHT", toX + 20, toY - 10);
//                case RIGHT_DOWN -> Main.g2D.drawString("RIGHT_DOWN", toX + 20, toY - 10);
//            }

            // make sure line segments are drawn from the centre of the stations
            fromX += this.LINE_OFFSET; fromY += this.LINE_OFFSET;
            newX1 += this.LINE_OFFSET; newY1 += this.LINE_OFFSET;
            newX += this.LINE_OFFSET; newY += this.LINE_OFFSET;
            newX2 += this.LINE_OFFSET; newY2 += this.LINE_OFFSET;
            toX += this.LINE_OFFSET; toY += this.LINE_OFFSET;

            // make the joining curve
            GeneralPath curve = new GeneralPath();
            curve.moveTo(newX1, newY1);
            curve.curveTo(newX1, newY1, newX, newY, newX2, newY2);

            // set up graphics for line segment drawing
            Main.g2D.setStroke(new BasicStroke(Main.mainFrame.getWidth() / 240f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Main.g2D.setColor(this.colour);

            // don't draw the curve if there's only one line segment to draw
            if (newX == fromX && newY == fromY || newX == toX && newY == toY) {
                // if there's only one segment, directions have to be shifted so line ends are the right direction
                if (newX == toX && newY == toY) secondDirection = firstDirection;
                else firstDirection = secondDirection;

                Main.g2D.drawLine(fromX, fromY, newX, newY);
                Main.g2D.drawLine(newX, newY, toX, toY);
            } else {
                Main.g2D.drawLine(fromX, fromY, newX1, newY1);
                Main.g2D.draw(curve);
                Main.g2D.drawLine(newX2, newY2, toX, toY);
            }

            // line ends have a square-ended stroke
            Main.g2D.setStroke(new BasicStroke(Main.mainFrame.getWidth() / 240f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

            // first station line end
            if (i == 1) {
                switch (firstDirection) {
                    case UP -> {
                        Main.g2D.drawLine(fromX, fromY, fromX, (int) (fromY + this.END_OFFSET * Math.sqrt(2)));
                        Main.g2D.drawLine((int) (fromX - this.CURVE_OFFSET * Math.sqrt(2)), (int) (fromY + this.END_OFFSET * Math.sqrt(2)), (int) (fromX + this.CURVE_OFFSET * Math.sqrt(2)), (int) (fromY + this.END_OFFSET * Math.sqrt(2)));
                    }
                    case DOWN -> {
                        Main.g2D.drawLine(fromX, fromY, fromX, (int) (fromY - this.END_OFFSET * Math.sqrt(2)));
                        Main.g2D.drawLine((int) (fromX - this.CURVE_OFFSET * Math.sqrt(2)), (int) (fromY - this.END_OFFSET * Math.sqrt(2)), (int) (fromX + this.CURVE_OFFSET * Math.sqrt(2)), (int) (fromY - this.END_OFFSET * Math.sqrt(2)));
                    }

                    case LEFT_UP -> {
                        Main.g2D.drawLine(fromX, fromY, fromX + this.END_OFFSET, fromY + this.END_OFFSET);
                        Main.g2D.drawLine(fromX + this.END_OFFSET + this.CURVE_OFFSET, fromY + this.END_OFFSET - this.CURVE_OFFSET, fromX + this.END_OFFSET - this.CURVE_OFFSET, fromY + this.END_OFFSET + this.CURVE_OFFSET);
                    }
                    case LEFT -> {
                        Main.g2D.drawLine(fromX, fromY, (int) (fromX + this.END_OFFSET * Math.sqrt(2)), fromY);
                        Main.g2D.drawLine((int) (fromX + this.END_OFFSET * Math.sqrt(2)), (int) (fromY - this.CURVE_OFFSET * Math.sqrt(2)), (int) (fromX + this.END_OFFSET * Math.sqrt(2)), (int) (fromY + this.CURVE_OFFSET * Math.sqrt(2)));
                    }
                    case LEFT_DOWN -> {
                        Main.g2D.drawLine(fromX, fromY, fromX + this.END_OFFSET, fromY - this.END_OFFSET);
                        Main.g2D.drawLine(fromX + this.END_OFFSET - this.CURVE_OFFSET, fromY - this.END_OFFSET - this.CURVE_OFFSET, fromX + this.END_OFFSET + this.CURVE_OFFSET, fromY - this.END_OFFSET + this.CURVE_OFFSET);
                    }

                    case RIGHT_UP -> {
                        Main.g2D.drawLine(fromX, fromY, fromX - this.END_OFFSET, fromY + this.END_OFFSET);
                        Main.g2D.drawLine(fromX - this.END_OFFSET - this.CURVE_OFFSET, fromY + this.END_OFFSET - this.CURVE_OFFSET, fromX - this.END_OFFSET + this.CURVE_OFFSET, fromY + this.END_OFFSET + this.CURVE_OFFSET);
                    }
                    case RIGHT -> {
                        Main.g2D.drawLine(fromX, fromY, (int) (fromX - this.END_OFFSET * Math.sqrt(2)), fromY);
                        Main.g2D.drawLine((int) (fromX - this.END_OFFSET * Math.sqrt(2)), (int) (fromY - this.CURVE_OFFSET * Math.sqrt(2)), (int) (fromX - this.END_OFFSET * Math.sqrt(2)), (int) (fromY + this.CURVE_OFFSET * Math.sqrt(2)));
                    }
                    case RIGHT_DOWN -> {
                        Main.g2D.drawLine(fromX, fromY, fromX - this.END_OFFSET, fromY - this.END_OFFSET);
                        Main.g2D.drawLine(fromX - this.END_OFFSET + this.CURVE_OFFSET, fromY - this.END_OFFSET - this.CURVE_OFFSET, fromX - this.END_OFFSET - this.CURVE_OFFSET, fromY - this.END_OFFSET + this.CURVE_OFFSET);
                    }
                }
            }

            // last station line end
            if (i == this.stations.size() - 1) {
                switch (secondDirection) {
                    case UP -> {
                        Main.g2D.drawLine(toX, toY, toX, (int) (toY - this.END_OFFSET * Math.sqrt(2)));
                        Main.g2D.drawLine((int) (toX - this.CURVE_OFFSET * Math.sqrt(2)), (int) (toY - this.END_OFFSET * Math.sqrt(2)), (int) (toX + this.CURVE_OFFSET * Math.sqrt(2)), (int) (toY - this.END_OFFSET * Math.sqrt(2)));
                    }
                    case DOWN -> {
                        Main.g2D.drawLine(toX, toY, toX, (int) (toY + this.END_OFFSET * Math.sqrt(2)));
                        Main.g2D.drawLine((int) (toX - this.CURVE_OFFSET * Math.sqrt(2)), (int) (toY + this.END_OFFSET * Math.sqrt(2)), (int) (toX + this.CURVE_OFFSET * Math.sqrt(2)), (int) (toY + this.END_OFFSET * Math.sqrt(2)));
                    }

                    case LEFT_UP -> {
                        Main.g2D.drawLine(toX, toY, toX - this.END_OFFSET, toY - this.END_OFFSET);
                        Main.g2D.drawLine(toX - this.END_OFFSET + this.CURVE_OFFSET, toY - this.END_OFFSET - this.CURVE_OFFSET, toX - this.END_OFFSET - this.CURVE_OFFSET, toY - this.END_OFFSET + this.CURVE_OFFSET);
                    }
                    case LEFT -> {
                        Main.g2D.drawLine(toX, toY, (int) (toX - this.END_OFFSET * Math.sqrt(2)), toY);
                        Main.g2D.drawLine((int) (toX - this.END_OFFSET * Math.sqrt(2)), (int) (toY - this.CURVE_OFFSET * Math.sqrt(2)), (int) (toX - this.END_OFFSET * Math.sqrt(2)), (int) (toY + this.CURVE_OFFSET * Math.sqrt(2)));
                    }
                    case LEFT_DOWN -> {
                        Main.g2D.drawLine(toX, toY, toX - this.END_OFFSET, toY + this.END_OFFSET);
                        Main.g2D.drawLine(toX - this.END_OFFSET - this.CURVE_OFFSET, toY + this.END_OFFSET - this.CURVE_OFFSET, toX - this.END_OFFSET + this.CURVE_OFFSET, toY + this.END_OFFSET + this.CURVE_OFFSET);
                    }

                    case RIGHT_UP -> {
                        Main.g2D.drawLine(toX, toY, toX + this.END_OFFSET, toY - this.END_OFFSET);
                        Main.g2D.drawLine(toX + this.END_OFFSET - this.CURVE_OFFSET, toY - this.END_OFFSET - this.CURVE_OFFSET, toX + this.END_OFFSET + this.CURVE_OFFSET, toY - this.END_OFFSET + this.CURVE_OFFSET);
                    }
                    case RIGHT -> {
                        Main.g2D.drawLine(toX, toY, (int) (toX + this.END_OFFSET * Math.sqrt(2)), toY);
                        Main.g2D.drawLine((int) (toX + this.END_OFFSET * Math.sqrt(2)), (int) (toY - this.CURVE_OFFSET * Math.sqrt(2)), (int) (toX + this.END_OFFSET * Math.sqrt(2)), (int) (toY + this.CURVE_OFFSET * Math.sqrt(2)));
                    }
                    case RIGHT_DOWN -> {
                        Main.g2D.drawLine(toX, toY, toX + this.END_OFFSET, toY + this.END_OFFSET);
                        Main.g2D.drawLine(toX + this.END_OFFSET + this.CURVE_OFFSET, toY + this.END_OFFSET - this.CURVE_OFFSET, toX + this.END_OFFSET - this.CURVE_OFFSET, toY + this.END_OFFSET + this.CURVE_OFFSET);
                    }
                }
            }
        }
    }

}
