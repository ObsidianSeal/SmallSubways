/*
 * TITLE: OtherUtilities
 * AUTHOR: Benjamin Gosselin
 * DATE: Thursday, May 30th, 2024
 * DESCRIPTION: I am fed up with only being able to put one argument in System.out.println();
 */

package utilities;

/**
 * Useful things.
 */
public class OtherUtilities {

    /**
     * Useful when you want to output multiple variables in one line.
     * @param objects All the things you want to print.
     */
    public static void debugPrint(Object ...objects) {
        for (Object object : objects) System.out.print("[" + object + "] ");
        System.out.println();
    }

}
