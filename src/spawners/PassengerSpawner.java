/*
 * TITLE: PassengerSpawner
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Makes passengers (small shapes) appear at stations.
 */

package spawners;

import main.Main;
import objects.Passenger;

/**
 * People to ride the subways.
 */
public class PassengerSpawner {

    private static final int SPAWN_CHECK_INTERVAL = 60;
    private static final int SPAWN_CHANCE = 15;
    private static int previousSpawnCheckTick;

    /**
     * Spawn passengers.
     */
    public static void passengerTick() {
        if (Main.tickRate != 0 && Main.ticks - previousSpawnCheckTick >= SPAWN_CHECK_INTERVAL) {
            if ((int) (Math.random() * SPAWN_CHANCE) == 0) {
                Main.stations.get((int) (Math.random() * Main.stations.size())).getPassengers().add(new Passenger());
            }
            previousSpawnCheckTick = Main.ticks;

//            OtherUtilities.debugPrint("passenger", Main.ticks, previousSpawnCheckTick);
        }
    }

}
