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
                        // lines
                        Color.decode("#A82222"),
                        Color.decode("#BA6BB0"),
                        Color.decode("#DDA15E"),
                        Color.decode("#BC6C25"),
                        Color.decode("#B8E0DD"),
                        Color.decode("#90DD93"),
                        Color.decode("#13064C"),
                        // map
                        Color.decode("#606C38"),
                        Color.decode("#FEFAE0"),
                        Color.decode("#283618"),
                        Color.decode("#4B7A98"),
                        Color.decode("#070230"),
                };
                this.data = new double[][]{};
            }
            case LONDON -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\london.png");
                this.colours = new Color[] {
                        // lines
                        Color.decode("#F69679"),
                        Color.decode("#7BCDC8"),
                        Color.decode("#7EA7D8"),
                        Color.decode("#A187BE"),
                        Color.decode("#F49AC2"),
                        Color.decode("#F6989D"),
                        Color.decode("#FDC68A"),
                        // map
                        Color.decode("#A6C48A"),
                        Color.decode("#F6E7CB"),
                        Color.decode("#678D58"),
                        Color.decode("#74D3AE"),
                        Color.decode("#1F392F"),
                };
                this.data = new double[][]{};
            }
            case OTTAWA -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\ottawa.png");
                this.colours = new Color[] {
                        // lines
                        Color.decode("#3B4C51"),
                        Color.decode("#75AA70"),
                        Color.decode("#E8A95E"),
                        Color.decode("#F67070"),
                        Color.decode("#E296D6"),
                        Color.decode("#8AEBD7"),
                        Color.decode("#CE834F"),
                        // map
                        Color.decode("#FDFCDC"),
                        Color.decode("#FED9B7"),
                        Color.decode("#F49C95"),
                        Color.decode("#00AFB9"),
                        Color.decode("#0081A7"),
                };
                this.data = new double[][]{};
            }
            case STRATFORD -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\stratford.png");
                this.colours = new Color[] {
                        // lines
                        Color.decode("#CE2424"),
                        Color.decode("#D688D9"),
                        Color.decode("#24BE6B"),
                        Color.decode("#EFB014"),
                        Color.decode("#254D9D"),
                        Color.decode("#791097"),
                        Color.decode("#975E10"),
                        // map
                        Color.decode("#ECDCC9"),
                        Color.decode("#EDD4B2"),
                        Color.decode("#D0A98F"),
                        Color.decode("#7476D3"),
                        Color.decode("#1B0961"),
                };
                this.data = new double[][]{};
            }
            case VICTORIA -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\victoria.png");
                this.colours = new Color[] {
                        // lines
                        Color.decode("#22D8BA"),
                        Color.decode("#38D822"),
                        Color.decode("#B6D822"),
                        Color.decode("#D8A122"),
                        Color.decode("#D83822"),
                        Color.decode("#2269D8"),
                        Color.decode("#070970"),
                        // map
                        Color.decode("#EBE1EF"),
                        Color.decode("#DAC7E2"),
                        Color.decode("#AF94DD"),
                        Color.decode("#F794CA"),
                        Color.decode("#772D6F"),
                };
                this.data = new double[][]{};
            }
            case WATERLOO -> {
                this.map = ImageUtilities.importImage("src\\images\\levels\\waterloo.png");
                this.colours = new Color[] {
                        // lines
                        Color.decode("#DCB35D"),
                        Color.decode("#8AC696"),
                        Color.decode("#B6C649"),
                        Color.decode("#D16666"),
                        Color.decode("#B67BC7"),
                        Color.decode("#EF99BC"),
                        Color.decode("#6AC8B7"),
                        // map
                        Color.decode("#2C4251"),
                        Color.decode("#C1C1C1"),
                        Color.decode("#122B3A"),
                        Color.decode("#6CCFF6"),
                        Color.decode("#4A71A9"),
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
