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

/**
 * Object containing map-specific info and images.
 */
public class MetroMap {
    private Image map;
    private Color[] colours;
    private boolean waterTravelType;

    // list of cities for level select
    public static String[] cities = {
            "Elora-Fergus",
            "London",
            "Montserrat",
            "Ottawa",
            "Stratford",
            "Victoria",
            "Waterloo"
    };

    // list of countries for level select
    public static String[] countries = {
            "CANADA",
            "CANADA",
            "MONTSERRAT",
            "CANADA",
            "CANADA",
            "CANADA",
            "CANADA"
    };

    /**
     * Create a map instance.
     */
    public MetroMap(Map map) {
        switch (map) {
            case ELORA_FERGUS -> {
                this.map = ImageUtilities.importImage("images/levels/elora-fergus.png");
                this.colours = new Color[]{
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
                        // locked line
                        Colour.GREY_10
                };
                this.waterTravelType = false;
            }
            case LONDON -> {
                this.map = ImageUtilities.importImage("images/levels/london.png");
                this.colours = new Color[]{
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
                        // locked line
                        Colour.GREY_20
                };
                this.waterTravelType = true;
            }
            case MONTSERRAT -> {
                this.map = ImageUtilities.importImage("images/levels/montserrat.png");
                this.colours = new Color[]{
                        // lines
                        Color.decode("#FF595E"),
                        Color.decode("#FFA83A"),
                        Color.decode("#6A4C93"),
                        Color.decode("#F7977A"),
                        Color.decode("#CD7AF7"),
                        Color.decode("#C5F3A8"),
                        Color.decode("#8CBFF3"),
                        // map
                        Color.decode("#A6C48A"),
                        Color.decode("#F9C8B6"),
                        Color.decode("#0C3D19"),
                        Color.decode("#1982C4"),
                        Color.decode("#1F185C"),
                        // locked line
                        Colour.GREY_40
                };
                this.waterTravelType = false;
            }
            case OTTAWA -> {
                this.map = ImageUtilities.importImage("images/levels/ottawa.png");
                this.colours = new Color[]{
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
                        // locked line
                        Colour.GREY_50
                };
                this.waterTravelType = true;
            }
            case STRATFORD -> {
                this.map = ImageUtilities.importImage("images/levels/stratford.png");
                this.colours = new Color[]{
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
                        // locked line
                        Colour.GREY_40
                };
                this.waterTravelType = false;
            }
            case VICTORIA -> {
                this.map = ImageUtilities.importImage("images/levels/victoria.png");
                this.colours = new Color[]{
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
                        // locked line
                        Colour.GREY_20
                };
                this.waterTravelType = false;
            }
            case WATERLOO -> {
                this.map = ImageUtilities.importImage("images/levels/waterloo.png");
                this.colours = new Color[]{
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
                        // locked line
                        Colour.GREY_30
                };
                this.waterTravelType = true;
            }
        }
    }

    /**
     * Get the level's background image.
     * @return The map's background BufferedImage.
     */
    public Image getMap() {
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
     * Get the level's water travel type.
     * @return True if the map prefers tunnels, false if the map prefers bridges.
     */
    public boolean getWaterTravelType() {
        return this.waterTravelType;
    }

}
