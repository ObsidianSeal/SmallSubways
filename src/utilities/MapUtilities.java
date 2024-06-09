/*
 * TITLE: MapUtilities
 * AUTHOR: Benjamin Gosselin
 * DATE: Saturday, June 1st, 2024
 * DESCRIPTION: Edit the grid to disallow stations from spawning at the edge or in water.
 */

package utilities;

import main.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Methods to help with maps.
 */
public class MapUtilities {

    /**
     * Make the whole grid "country".
     */
    public static void initializeGrid() {
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 80; j++) {
                Main.grid[i][j] = Main.COUNTRY;
            }
        }
    }

    /**
     * Prevent stations from spawning too close to the edge of the map.
     */
    public static void disallowEdge() {
        for (int i = 0; i < 45; i++) {
            for (int j = 0; j < 80; j++) {
                if ((i < 2 || i > 42) || (j < 2 || j > 77)) {
                    Main.grid[i][j] = Main.MARGIN;
                }
            }
        }
    }

    /**
     * Prevent stations from spawning below menus.
     */
    public static void disallowMenuAreas() {
        // back arrow area
        for (int i = 0; i < 6; i++) {
            for (int j = 2; j < 12; j++) {
                Main.grid[i][j] = Main.MARGIN;
            }
        }

        // clock & points area
        for (int i = 0; i < 6; i++) {
            for (int j = 64; j < 80; j++) {
                Main.grid[i][j] = Main.MARGIN;
            }
        }

        // resources area
        for (int i = 38; i < 45; i++) {
            for (int j = 2; j < 20; j++) {
                Main.grid[i][j] = Main.MARGIN;
            }
        }

        // line selection area
        for (int i = 38; i < 45; i++) {
            for (int j = 54; j < 80; j++) {
                Main.grid[i][j] = Main.MARGIN;
            }
        }
    }

    /**
     * Prevent stations from spawning on water.
     */
    public static void disallowWater() {
        BufferedImage map = (BufferedImage) Main.map.getMap();

        int mapHeight = map.getHeight();
        int mapWidth = map.getWidth();

        // go through every pixel and check its colour against WATER and COUNTRY
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                Color colour = new Color(map.getRGB(j, i));

                if (colour.equals(Main.map.getColours()[10])) Main.grid[i / (mapHeight / 45)][j / (mapWidth / 80)] = Main.WATER;
                if (colour.equals(Main.map.getColours()[8]) && Main.grid[i / (mapHeight / 45)][j / (mapWidth / 80)] == Main.COUNTRY) Main.grid[i / (mapHeight / 45)][j / (mapWidth / 80)] = Main.CITY;
            }
        }
    }

}
