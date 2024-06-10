/*
 * TITLE: Direction
 * AUTHOR: Benjamin Gosselin
 * DATE: June 9th, 2024
 * DESCRIPTION: Represents the ways a line can come from a station.
 */

package enums;

/**
 * The eight primary directions of a line from a given station.
 */
public enum Direction {
    UP,
    DOWN,

    LEFT_UP,
    LEFT,
    LEFT_DOWN,

    RIGHT_UP,
    RIGHT,
    RIGHT_DOWN
}