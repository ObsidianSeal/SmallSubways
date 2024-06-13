/*
 * TITLE: PassengerSpawner
 * AUTHOR: Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Makes passengers (small shapes) appear at stations.
 */

package spawners;

import enums.Shape;
import main.Main;
import objects.Passenger;
import objects.Station;
import utilities.SoundUtilities;

import java.util.HashMap;

/**
 * People to ride the subways.
 */
public class PassengerSpawner {

    private static final int SPAWN_CHECK_INTERVAL = 200;
    private static final int SPAWN_CHANCE = 15;
    private static HashMap<Station, Integer> previousSpawnCheckTicks = new HashMap<Station, Integer>();
    private static SoundUtilities soundUtilities = new SoundUtilities(); // Create an instance of SoundUtilities

    /**
     * Spawn passengers.
     */
    public static void passengerTick(Station station) {
        if (Main.ticks == 0) previousSpawnCheckTicks.clear(); // clear if restarted
        if (!previousSpawnCheckTicks.containsKey(station)) previousSpawnCheckTicks.put(station, 0); // initialize values

        // enough time has passed?
        if (Main.tickRate != 0 && Main.ticks - previousSpawnCheckTicks.get(station) >= SPAWN_CHECK_INTERVAL) {
            // random chance?
            if ((int) (Math.random() * SPAWN_CHANCE) == 0) {
                Shape type;

                do {
                    type = Shape.values()[(int) (Math.random() * Shape.values().length)];
                } while (type == station.getType() || !Main.shapesPresent.contains(type)); // passengers should only spawn of shapes that have appeared on the map, and not of their own station

                station.getPassengers().add(new Passenger(type));
            }
            soundUtilities.setFile(0); // Set the first sound
            soundUtilities.play(); // Play the sound

            // update last checked tick
            previousSpawnCheckTicks.put(station, Main.ticks);
        }
    }

}
