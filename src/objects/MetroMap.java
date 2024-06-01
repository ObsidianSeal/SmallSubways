/*
 * TITLE: MetroMap
 * AUTHOR: Daniel Zhong, Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Map-related information, variables, data, constants, etc.
 */

package objects;

import enums.Map;
import utilities.ImageUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Object containing map-specific info and images.
 */
public class MetroMap {
    private BufferedImage map;
    private Color[] colours;
    private double[][] data;

    /**
     * Create a map instance.
     */
    public MetroMap(Map map) {
        switch (map) {
            case ELORA_FERGUS -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\elora-fergus.png");
                this.colours = new Color[] {
                        Color.decode("#A82222"),
                        Color.decode("#BA6BB0"),
                        Color.decode("#DDA15E"),
                        Color.decode("#BC6C25"),
                        Color.decode("#B8E0DD"),
                        Color.decode("#90DD93"),
                        Color.decode("#13064C"),
                };
                this.data = new double[][]{};
            }
            case LONDON -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\london.png");
                this.colours = new Color[] {
                        Color.decode("#f69679"),
                        Color.decode("#7bcdc8"),
                        Color.decode("#7ea7d8"),
                        Color.decode("#a187be"),
                        Color.decode("#f49ac2"),
                        Color.decode("#f6989d"),
                        Color.decode("#fdc68a"),
                };
                this.data = new double[][]{};
            }
            case OTTAWA -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\ottawa.png");
                this.colours = new Color[] {
                        Color.decode("#3B4C51"),
                        Color.decode("#75AA70"),
                        Color.decode("#E8A95E"),
                        Color.decode("#F67070"),
                        Color.decode("#E296D6"),
                        Color.decode("#8AEBD7"),
                        Color.decode("#CE834F"),
                };
                this.data = new double[][]{};
            }
            case STRATFORD -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\stratford.png");
                this.colours = new Color[] {
                        Color.decode("#CE2424"),
                        Color.decode("#D688D9"),
                        Color.decode("#24BE6B"),
                        Color.decode("#EFB014"),
                        Color.decode("#254D9D"),
                        Color.decode("#791097"),
                        Color.decode("#975E10"),
                };
                this.data = new double[][]{};
            }
            case VICTORIA -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\victoria.png");
                this.colours = new Color[] {
                        Color.decode("#22D8BA"),
                        Color.decode("#38D822"),
                        Color.decode("#B6D822"),
                        Color.decode("#D8A122"),
                        Color.decode("#D83822"),
                        Color.decode("#2269D8"),
                        Color.decode("#070970"),
                };
                this.data = new double[][]{};
            }
            case WATERLOO -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\waterloo.png");
                this.colours = new Color[] {
                        Color.decode("#DCB35D"),
                        Color.decode("#8AC696"),
                        Color.decode("#B6C649"),
                        Color.decode("#D16666"),
                        Color.decode("#B67BC7"),
                        Color.decode("#EF99BC"),
                        Color.decode("#6AC8B7"),
                };
                this.data = new double[][]{};
            }
        }
    }

    /**
     * Get the level's background image.
     * @return The map's background BufferedImage.
     */
    public BufferedImage getMap() {
        return this.map;
    }

    /**
     * Get the level's colour scheme.
     * @return The map's colour scheme.
     */
    public Color[] getColours() {
        return this.colours;
    }

    /**
     * Get the level's data.
     * @return The map's data.
     */
    public double[][] getData() {
        return this.data;
    }

}
