/*
 * TITLE: StationSpawner
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Makes stations (shapes) appear on the map.
 */

package spawners;

import main.Main;
import objects.Station;

/**
 * Stations for passengers to go to.
 */
public class StationSpawner {

    private static final int SPAWN_CHECK_INTERVAL = 200;
    private static final int SPAWN_CHANCE = 15;
    private static int previousSpawnCheckTick;

    /**
     * Spawn stations until no more can be spawned.
     */
    public static void stationTick() {
        // space to spawn?
        if (Main.openCount > 1) {
            // enough time has passed?
            if (Main.tickRate != 0 && Main.ticks - previousSpawnCheckTick >= SPAWN_CHECK_INTERVAL) {
                // random chance?
                if ((int) (Math.random() * SPAWN_CHANCE) == 0) {
                    Main.stations.add(new Station());
                }

                // update last checked tick
                previousSpawnCheckTick = Main.ticks;
            }
        }
    }

}
