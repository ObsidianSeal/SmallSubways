/*
 * TITLE: Locomotive
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Represents a locomotive, a train car that drives around.
 */

package objects;

/**
 * Primary train class.
 */
public class Locomotive extends Train {

    /**
     * Locomotive constructor.
     * @param line The line that the train is to be on.
     */
    public Locomotive(MetroLine line) {
        super(line);
    }

}
