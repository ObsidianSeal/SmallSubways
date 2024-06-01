/*
 * TITLE: MapUtilities
 * AUTHOR: Benjamin Gosselin
 * DATE: Saturday, June 1st, 2024
 * DESCRIPTION: Edit the grid to disallow stations from spawning at the edge or in water.
 */

package utilities;

import main.Main;

/**
 * Methods to help with maps.
 */
public class MapUtilities {

    /**
     * Prevent stations from spawning too close to the edge of the map.
     */
    public static void disallowEdge() {
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 80; j++) {
                if ((i < 2 || i > 42) || (j < 2 || j > 77)) {
                    Main.grid[i][j] = true;
                }
            }
        }
    }

    /**
     * Prevent stations from spawning on water.
     */
    public static void disallowWater() {

    }

}
