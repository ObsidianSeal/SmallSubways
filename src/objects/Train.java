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

/**
 * Train game, now with trains.
 */
public abstract class Train {
    private MetroLine line;
    private Station fromStation, toStation;
    private boolean travelDirection;
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

        // initial 'to' and 'from' stations
        this.fromStation = this.line.getStations().get(0);
        this.toStation = this.line.getStations().get(1);

        // begin forwards
        this.travelDirection = true;

        this.x1 = this.fromStation.getX();
        this.y1 = this.fromStation.getY();

        this.x2 = this.x1;
        this.y2 = this.y1;
    }

    /**
     * Move the train. MUCH OF THE CODE IS COPIED FROM MetroLine.
     */
    public void move() {
//        Main.g2D.setColor(Color.BLACK); Main.g2D.setFont(Main.robotoSerifLight16);

        // the two directions
        Direction firstDirection, secondDirection;

        // current station values
        int fromX = (int) this.fromStation.getX();
        int fromY = (int) this.fromStation.getY();
        int toX = (int) this.toStation.getX();
        int toY = (int) this.toStation.getY();
        boolean diagonal;

        if (travelDirection) diagonal = this.toStation.isDiagonal(this.line); // forwards: get the 'diagonal' of the destination station
        else diagonal = !this.fromStation.isDiagonal(this.line); // reverse: get the opposite 'diagonal' of the origin station

        // reached destination station?
        if (Math.abs(this.x1 - fromX) >= Math.abs(toX - fromX) && Math.abs(this.y1 - fromY) >= Math.abs(toY - fromY)) {
            // if just arrived, start waiting at this time
            if (!waiting) {
                this.waiting = true;
                this.waitTick = Main.ticks;

                // for now, you get points just by picking passengers up
                Main.points += toStation.getPassengers().size();
                toStation.getPassengers().clear();
            }

            // if it has been long enough, stop waiting
            if (Main.ticks < this.waitTick + this.WAIT_TIME) return;
            else waiting = false;

            if (travelDirection) {
                // too far? switch direction
                if (toStation == this.line.getStations().getLast()) {
                    fromStation = toStation;
                    toStation = this.line.getStations().get(this.line.getStations().size() - 2);

                    this.travelDirection = false;
                } else {
                    fromStation = this.line.getStations().get(this.line.getStations().indexOf(fromStation) + 1);
                    toStation = this.line.getStations().get(this.line.getStations().indexOf(toStation) + 1);
                }
            } else {
                // too far? switch direction
                if (toStation == this.line.getStations().getFirst()) {
                    fromStation = toStation;
                    toStation = this.line.getStations().get(1);

                    this.travelDirection = true;
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
            if (travelDirection) diagonal = this.toStation.isDiagonal(this.line);
            else diagonal = !this.fromStation.isDiagonal(this.line);
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

        // for each of the eight directions...
        switch (moveDirection) {
            case UP -> {
                if (this.travelDirection) {
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
            case DOWN -> {
                if (this.travelDirection) {
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

            case LEFT_UP -> {
                this.x1 -= this.MOVE_DIAGONAL * Main.tickRate;
                this.y1 -= this.MOVE_DIAGONAL * Main.tickRate;

                this.x2 = this.x1 - this.TRAIN_SIZE_DIAGONAL;
                this.y2 = this.y1 - this.TRAIN_SIZE_DIAGONAL;
            }
            case LEFT -> {
                this.x1 -= this.MOVE_STRAIGHT * Main.tickRate;
                if (this.travelDirection) {
                    if (diagonal) this.y1 = toY;
                    else this.y1 = fromY;
                } else {
                    if (diagonal) this.y1 = toY;
                    else this.y1 = fromY;
                }

                this.x2 = this.x1 - this.TRAIN_SIZE_STRAIGHT;
                this.y2 = this.y1;
            }
            case LEFT_DOWN -> {
                this.x1 -= this.MOVE_DIAGONAL * Main.tickRate;
                this.y1 += this.MOVE_DIAGONAL * Main.tickRate;

                this.x2 = this.x1 - this.TRAIN_SIZE_DIAGONAL;
                this.y2 = this.y1 + this.TRAIN_SIZE_DIAGONAL;
            }

            case RIGHT_UP -> {
                this.x1 += this.MOVE_DIAGONAL * Main.tickRate;
                this.y1 -= this.MOVE_DIAGONAL * Main.tickRate;

                this.x2 = this.x1 + this.TRAIN_SIZE_DIAGONAL;
                this.y2 = this.y1 - this.TRAIN_SIZE_DIAGONAL;
            }
            case RIGHT -> {
                this.x1 += this.MOVE_STRAIGHT * Main.tickRate;
                if (this.travelDirection) {
                    if (diagonal) this.y1 = toY;
                    else this.y1 = fromY;
                } else {
                    if (diagonal) this.y1 = toY;
                    else this.y1 = fromY;
                }

                this.x2 = this.x1 + this.TRAIN_SIZE_STRAIGHT;
                this.y2 = this.y1;
            }
            case RIGHT_DOWN -> {
                this.x1 += this.MOVE_DIAGONAL * Main.tickRate;
                this.y1 += this.MOVE_DIAGONAL * Main.tickRate;

                this.x2 = this.x1 + this.TRAIN_SIZE_DIAGONAL;
                this.y2 = this.y1 + this.TRAIN_SIZE_DIAGONAL;
            }
        }

//        Main.g2D.drawString(String.format("from: %d, to: %d, moveDirection: " + moveDirection + ", travelDirection: %b, diagonal: %b", this.line.getStations().indexOf(this.fromStation), this.line.getStations().indexOf(this.toStation), this.travelDirection, diagonal), (int) (this.x1 + 50), (int) (this.y1 + 50));
    }

    /**
     * Draw the train.
     */
    public void draw() {
        Main.g2D.setColor(this.line.getColour());
        Main.g2D.setStroke(new BasicStroke((float) (Main.gridSize * 0.8), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        Main.g2D.drawLine((int) (this.x1 + this.line.LINE_OFFSET), (int) (this.y1 + this.line.LINE_OFFSET), (int) (this.x2 + this.line.LINE_OFFSET), (int) (this.y2 + this.line.LINE_OFFSET));
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
