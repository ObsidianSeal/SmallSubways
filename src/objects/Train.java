/*
 * TITLE: Train
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Represents a train.
 */

package objects;

import enums.Direction;
import main.Main;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

/**
 * Train game, now with trains.
 */
public abstract class Train {
    private MetroLine line;
    private Station fromStation, toStation;
    private ArrayList<Passenger> passengers;
    private boolean isTravellingForward;
    private double x1, y1, x2, y2;
    private int waitTick;
    private boolean waiting;

    public final double TRAIN_SIZE_DIAGONAL = Main.gridSize;
    public final double TRAIN_SIZE_STRAIGHT = (this.TRAIN_SIZE_DIAGONAL * Math.sqrt(2));

    public final double MOVE_DIAGONAL = 0.5;
    public final double MOVE_STRAIGHT = (this.MOVE_DIAGONAL * Math.sqrt(2));

    public final int WAIT_TIME = 100;

    /**
     * Train constructor.
     * @param line The line that the train is to be on.
     */
    Train(MetroLine line) {
        this.line = line;

        // passengers
        this.passengers = new ArrayList<Passenger>();

        // initial 'to' and 'from' stations
        this.fromStation = this.line.getStations().get(0);
        this.toStation = this.line.getStations().get(1);

        // begin forwards
        this.isTravellingForward = true;

        this.x1 = this.fromStation.getX();
        this.y1 = this.fromStation.getY();

        this.x2 = this.x1;
        this.y2 = this.y1;
    }

    /**
     * Move (and draw) the train. The main part is modified from MetroLine. This code is pretty awful, but I don't have time to improve it.
     */
    public void moveAndDraw() {
        Main.g2D.setColor(Color.BLACK); Main.g2D.setFont(Main.robotoSerifLight16);

        // the two directions
        Direction firstDirection, secondDirection;

        // current station values
        int fromX = (int) this.fromStation.getX();
        int fromY = (int) this.fromStation.getY();
        int toX = (int) this.toStation.getX();
        int toY = (int) this.toStation.getY();
        boolean diagonal;

        if (isTravellingForward) diagonal = this.toStation.isDiagonal(this.line); // forwards: get the 'diagonal' of the destination station
        else diagonal = !this.fromStation.isDiagonal(this.line); // reverse: get the opposite 'diagonal' of the origin station

        // reached destination station?
        if (Math.abs(this.x1 - fromX) >= Math.abs(toX - fromX) && Math.abs(this.y1 - fromY) >= Math.abs(toY - fromY)) {
            // if just arrived, start waiting at this time
            if (!waiting) {
                this.waiting = true;
                this.waitTick = Main.ticks;

                ArrayList<Station> thisLineStations = this.line.getStations();

                int indexOfStation = thisLineStations.indexOf(this.toStation);

                for (int j = 0; j < toStation.getPassengers().size(); j++) {
                    Passenger passenger = toStation.getPassengers().get(j);
                    if (passenger.getType() == toStation.getType()) {
                        this.passengers.remove(passenger);
                        System.out.println("Dropped passenger of type " + passenger.getType());
                        Main.points++;
                    }
                }

                for (int j = 0; j < toStation.getPassengers().size(); j++) {
                    Passenger passenger = toStation.getPassengers().get(j);
                    if (this.isTravellingForward) {
                        for (int i = indexOfStation; i < thisLineStations.size(); i++) {
                            if (thisLineStations.get(i).getType() == passenger.getType() && passengers.size() < 6) {
                                this.passengers.add(passenger);
                                System.out.println("Picked up passenger of type " + passenger.getType());
                                toStation.getPassengers().remove(passenger);
                            }
                        }
                    } else {
                        for (int i = indexOfStation; i >= 0; i--) {
                            if (thisLineStations.get(i).getType() == passenger.getType() && passengers.size() < 6) {
                                passengers.add(passenger);
                                System.out.println("Picked up passenger of type " + passenger.getType());
                                toStation.getPassengers().remove(passenger);
                            }
                        }
                    }
                }
            }

            // if it has been long enough, stop waiting
            if (Main.ticks >= this.waitTick + this.WAIT_TIME) waiting = false;

            if (!waiting) {
                if (isTravellingForward) {
                    // too far? switch direction
                    if (toStation == this.line.getStations().getLast()) {
                        fromStation = toStation;
                        toStation = this.line.getStations().get(this.line.getStations().size() - 2);

                        this.isTravellingForward = false;
                    } else {
                        fromStation = this.line.getStations().get(this.line.getStations().indexOf(fromStation) + 1);
                        toStation = this.line.getStations().get(this.line.getStations().indexOf(toStation) + 1);
                    }
                } else {
                    // too far? switch direction
                    if (toStation == this.line.getStations().getFirst()) {
                        fromStation = toStation;
                        toStation = this.line.getStations().get(1);

                        this.isTravellingForward = true;
                    } else {
                        fromStation = this.line.getStations().get(this.line.getStations().indexOf(fromStation) - 1);
                        toStation = this.line.getStations().get(this.line.getStations().indexOf(toStation) - 1);
                    }
                }

                // update 'from' and 'to' variables
                fromX = (int) this.fromStation.getX();
                fromY = (int) this.fromStation.getY();
                toX = (int) this.toStation.getX();
                toY = (int) this.toStation.getY();

                // update 'diagonal' (refer to the first use of this code for more information)
                if (isTravellingForward) diagonal = this.toStation.isDiagonal(this.line);
                else diagonal = !this.fromStation.isDiagonal(this.line);
            }
        }

//        Main.g2D.drawString(String.format("from: (%d, %d), to: (%d, %d), current: (%d, %d), diagonal: %b", fromX, fromY, toX, toY, (int) (this.x1), (int) (this.y1), diagonal), (int) (this.x1 + 50), (int) (this.y1));

        // calculations
        int xDiff = toX - fromX;
        int yDiff = toY - fromY;
        int xDiff2 = Math.abs(toX - fromX);
        int yDiff2 = Math.abs(toY - fromY);
        boolean xLonger = xDiff2 >= yDiff2;
        int newX, newY;

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
                    } else {
                        // DOWN
                        firstDirection = Direction.DOWN;
                        secondDirection = Direction.RIGHT_DOWN;

                        newX = fromX;
                        newY = fromY + (yDiff2 - xDiff2);
                    }
                } else {
                    // RIGHT-UP
                    if (xLonger) {
                        // RIGHT
                        firstDirection = Direction.RIGHT;
                        secondDirection = Direction.RIGHT_UP;

                        newX = fromX + (xDiff2 - yDiff2);
                        newY = fromY;
                    } else {
                        // UP
                        firstDirection = Direction.UP;
                        secondDirection = Direction.RIGHT_UP;

                        newX = fromX;
                        newY = fromY - (yDiff2 - xDiff2);
                    }
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
                    } else {
                        // DOWN
                        firstDirection = Direction.DOWN;
                        secondDirection = Direction.LEFT_DOWN;

                        newX = fromX;
                        newY = fromY + (yDiff2 - xDiff2);
                    }
                } else {
                    // LEFT-UP
                    if (xLonger) {
                        // LEFT
                        firstDirection = Direction.LEFT;
                        secondDirection = Direction.LEFT_UP;

                        newX = fromX - (xDiff2 - yDiff2);
                        newY = fromY;
                    } else {
                        // UP
                        firstDirection = Direction.UP;
                        secondDirection = Direction.LEFT_UP;

                        newX = fromX;
                        newY = fromY - (yDiff2 - xDiff2);
                    }
                }
            }
        } else {
            int distance = Math.min(xDiff2, yDiff2);

            if (xDiff >= 0) {
                newX = fromX + distance;
            } else {
                newX = fromX - distance;
            }

            if (yDiff >= 0) {
                newY = fromY + distance;
            } else {
                newY = fromY - distance;
            }

            if (xDiff >= 0) {
                // RIGHT
                if (yDiff >= 0) {
                    // RIGHT-DOWN
                    if (xLonger) {
                        // RIGHT
                        firstDirection = Direction.RIGHT_DOWN;
                        secondDirection = Direction.RIGHT;
                    } else {
                        // DOWN
                        firstDirection = Direction.RIGHT_DOWN;
                        secondDirection = Direction.DOWN;
                    }
                } else {
                    // RIGHT-UP
                    if (xLonger) {
                        // RIGHT
                        firstDirection = Direction.RIGHT_UP;
                        secondDirection = Direction.RIGHT;
                    } else {
                        // UP
                        firstDirection = Direction.RIGHT_UP;
                        secondDirection = Direction.UP;
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
                    } else {
                        // DOWN
                        firstDirection = Direction.LEFT_DOWN;
                        secondDirection = Direction.DOWN;
                    }
                } else {
                    // LEFT-UP
                    if (xLonger) {
                        // LEFT
                        firstDirection = Direction.LEFT_UP;
                        secondDirection = Direction.LEFT;
                    } else {
                        // UP
                        firstDirection = Direction.LEFT_UP;
                        secondDirection = Direction.UP;
                    }
                }
            }
        }

//        Main.g2D.drawString(firstDirection + ", " + secondDirection, (int) (this.x1 + 50), (int) (this.y1 + 25));

        // if there's only one line segment (visually), discard the invisible one
        if (newX == fromX && newY == fromY || newX == toX && newY == toY) {
            if (newX == toX && newY == toY) secondDirection = firstDirection;
            else firstDirection = secondDirection;
        }

//        Main.g2D.setStroke(new BasicStroke(1));
//        Main.g2D.drawLine(fromX + this.line.LINE_OFFSET, fromY + this.line.LINE_OFFSET, newX + this.line.LINE_OFFSET, newY + this.line.LINE_OFFSET);
//        Main.g2D.drawLine(newX + this.line.LINE_OFFSET, newY + this.line.LINE_OFFSET, toX + this.line.LINE_OFFSET, toY + this.line.LINE_OFFSET);

        // determine which line segment the train is on
        Direction moveDirection;
        if (Math.abs(this.x1 - fromX) >= Math.abs(newX - fromX) && Math.abs(this.y1 - fromY) >= Math.abs(newY - fromY)) moveDirection = secondDirection;
        else moveDirection = firstDirection;

        Main.g2D.setColor(this.line.getColour());
        Main.g2D.setStroke(new BasicStroke((float) (Main.gridSize * 0.8), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        GeneralPath trainPath = new GeneralPath();

        // for each of the eight directions...
        switch (moveDirection) {
            case UP -> {
                if (!waiting) {
                    if (this.isTravellingForward) {
                        if (diagonal) this.x1 = toX;
                        else this.x1 = fromX;
                    } else {
                        if (diagonal) this.x1 = toX;
                        else this.x1 = fromX;
                    }
                    this.y1 -= this.MOVE_STRAIGHT * Main.tickRate;

                    this.x2 = this.x1;
                    this.y2 = this.y1 - this.TRAIN_SIZE_STRAIGHT;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET, this.y1 + this.line.LINE_OFFSET + this.TRAIN_SIZE_STRAIGHT / 2.0);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET, this.y2 + this.line.LINE_OFFSET + this.TRAIN_SIZE_STRAIGHT / 2.0);
            }
            case DOWN -> {
                if (!waiting) {
                    if (this.isTravellingForward) {
                        if (diagonal) this.x1 = toX;
                        else this.x1 = fromX;
                    } else {
                        if (diagonal) this.x1 = toX;
                        else this.x1 = fromX;
                    }
                    this.y1 += this.MOVE_STRAIGHT * Main.tickRate;

                    this.x2 = this.x1;
                    this.y2 = this.y1 + this.TRAIN_SIZE_STRAIGHT;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET, this.y1 + this.line.LINE_OFFSET - this.TRAIN_SIZE_STRAIGHT / 2.0);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET, this.y2 + this.line.LINE_OFFSET - this.TRAIN_SIZE_STRAIGHT / 2.0);
            }

            case LEFT_UP -> {
                if (!waiting) {
                    this.x1 -= this.MOVE_DIAGONAL * Main.tickRate;
                    this.y1 -= this.MOVE_DIAGONAL * Main.tickRate;

                    this.x2 = this.x1 - this.TRAIN_SIZE_DIAGONAL;
                    this.y2 = this.y1 - this.TRAIN_SIZE_DIAGONAL;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0, this.y1 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0, this.y2 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0);
            }
            case LEFT -> {
                if (!waiting) {
                    this.x1 -= this.MOVE_STRAIGHT * Main.tickRate;
                    if (this.isTravellingForward) {
                        if (diagonal) this.y1 = toY;
                        else this.y1 = fromY;
                    } else {
                        if (diagonal) this.y1 = toY;
                        else this.y1 = fromY;
                    }
                    this.x2 = this.x1 - this.TRAIN_SIZE_STRAIGHT;
                    this.y2 = this.y1;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET + this.TRAIN_SIZE_STRAIGHT / 2.0, this.y1 + this.line.LINE_OFFSET);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET + this.TRAIN_SIZE_STRAIGHT / 2.0, this.y2 + this.line.LINE_OFFSET);
            }
            case LEFT_DOWN -> {
                if (!waiting) {
                    this.x1 -= this.MOVE_DIAGONAL * Main.tickRate;
                    this.y1 += this.MOVE_DIAGONAL * Main.tickRate;

                    this.x2 = this.x1 - this.TRAIN_SIZE_DIAGONAL;
                    this.y2 = this.y1 + this.TRAIN_SIZE_DIAGONAL;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0, this.y1 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0, this.y2 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0);
            }

            case RIGHT_UP -> {
                if (!waiting) {
                    this.x1 += this.MOVE_DIAGONAL * Main.tickRate;
                    this.y1 -= this.MOVE_DIAGONAL * Main.tickRate;

                    this.x2 = this.x1 + this.TRAIN_SIZE_DIAGONAL;
                    this.y2 = this.y1 - this.TRAIN_SIZE_DIAGONAL;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0, this.y1 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0, this.y2 + this.line.LINE_OFFSET + this.TRAIN_SIZE_DIAGONAL / 2.0);
            }
            case RIGHT -> {
                if (!waiting) {
                    this.x1 += this.MOVE_STRAIGHT * Main.tickRate;
                    if (this.isTravellingForward) {
                        if (diagonal) this.y1 = toY;
                        else this.y1 = fromY;
                    } else {
                        if (diagonal) this.y1 = toY;
                        else this.y1 = fromY;
                    }

                    this.x2 = this.x1 + this.TRAIN_SIZE_STRAIGHT;
                    this.y2 = this.y1;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET - this.TRAIN_SIZE_STRAIGHT / 2.0, this.y1 + this.line.LINE_OFFSET);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET - this.TRAIN_SIZE_STRAIGHT / 2.0, this.y2 + this.line.LINE_OFFSET);
            }
            case RIGHT_DOWN -> {
                if (!waiting) {
                    this.x1 += this.MOVE_DIAGONAL * Main.tickRate;
                    this.y1 += this.MOVE_DIAGONAL * Main.tickRate;

                    this.x2 = this.x1 + this.TRAIN_SIZE_DIAGONAL;
                    this.y2 = this.y1 + this.TRAIN_SIZE_DIAGONAL;
                }

                trainPath.moveTo(this.x1 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0, this.y1 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0);
                trainPath.lineTo(this.x2 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0, this.y2 + this.line.LINE_OFFSET - this.TRAIN_SIZE_DIAGONAL / 2.0);
            }
        }

        Main.g2D.draw(trainPath);

//        Main.g2D.drawString(String.format("from: %d, to: %d, moveDirection: " + moveDirection + ", travelDirection: %b, diagonal: %b", this.line.getStations().indexOf(this.fromStation), this.line.getStations().indexOf(this.toStation), this.travelDirection, diagonal), (int) (this.x1 + 50), (int) (this.y1 + 50));
    }

    /**
     * Get the train's origin.
     * @return The train's current origin.
     */
    public Station getFromStation() {
        return this.fromStation;
    }

    /**
     * Get the train's destination.
     * @return The train's current destination.
     */
    public Station getToStation() {
        return this.toStation;
    }

}
