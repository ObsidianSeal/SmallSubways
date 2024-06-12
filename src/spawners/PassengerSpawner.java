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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * People to ride the subways.
 */
public class PassengerSpawner {

    private static final int SPAWN_CHECK_INTERVAL = 200;
    private static final int SPAWN_CHANCE = 15;
    private static HashMap<Station, Integer> previousSpawnCheckTicks = new HashMap<Station, Integer>();

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

                ArrayList<Shape> usedShapes = new ArrayList<Shape>();

                for (Station s : Main.stations) {
                    if (!usedShapes.contains(s.getType())) {
                        usedShapes.add(s.getType());
                    }
                }

                do {
                    type = usedShapes.get((int) (Math.random() * usedShapes.size()));
                } while (type == station.getType() || !Main.shapesPresent.contains(type)); // passengers should only spawn of shapes that have appeared on the map, and not of their own station

                station.getPassengers().add(new Passenger(type));
            }

            // update last checked tick
            previousSpawnCheckTicks.put(station, Main.ticks);
        }
    }

}
