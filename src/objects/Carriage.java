/*
 * TITLE: Carriage
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Represents a carriage, a train car that can only follow a locomotive.
 */

package objects;

/**
 * Secondary train class, unfinished and unimplemented.
 */
public class Carriage extends Train {
    private Locomotive locomotive;

    /**
     * Carriage constructor.
     * @param line The line that the carriage is to be on.
     * @param locomotive The locomotive to attach to.
     */
    public Carriage(MetroLine line, Locomotive locomotive) {
        super(line);
        this.locomotive = locomotive;
    }

}
