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

    private static final int SPAWN_CHECK_INTERVAL = 120;
    private static final int SPAWN_CHANCE = 15;
    private static int previousSpawnCheckTick;

    /**
     * Spawn stations until no more can be spawned.
     */
    public static void stationTick() {
        if (Main.openCount > 1) {
            if (Main.tickRate != 0 && Main.ticks - previousSpawnCheckTick >= SPAWN_CHECK_INTERVAL) {
                if ((int) (Math.random() * SPAWN_CHANCE) == 0) {
                    Main.stations.add(new Station());
                }
                previousSpawnCheckTick = Main.ticks;

//                OtherUtilities.debugPrint("station", Main.ticks, previousSpawnCheckTick);
            }
        }
    }

}
