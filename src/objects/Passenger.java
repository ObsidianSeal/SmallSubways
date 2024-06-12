/*
 * TITLE: Passenger
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Shapes waiting at stations or riding trains.
 */

package objects;

import enums.Shape;

/**
 * Object representing a passenger.
 */
public class Passenger {
    private Shape type;

    /**
     * Random passenger constructor.
     */
    public Passenger() {
        this.type = Shape.values()[(int) (Math.random() * Shape.values().length)];
    }

    /**
     * Passenger constructor.
     */
    public Passenger(Shape type) {
        this.type = type;
    }

    /**
     * Get the passenger's type.
     * @return The passenger's current type.
     */
    public Shape getType() {
        return this.type;
    }

    /**
     * Set the passenger's type.
     * @param type The passenger's new type.
     */
    public void setType(Shape type) {
        this.type = type;
    }

}
