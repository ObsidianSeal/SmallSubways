/*
 * TITLE: MetroMap
 * AUTHOR: Daniel Zhong, Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Map-related information, variables, data, constants, etc.
 */

package objects;

import utilities.ImageUtilities;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Object containing map-specific info and images.
 */
public class MetroMap {
    public final static BufferedImage ELORA_FERGUS = ImageUtilities.importImage("src\\images\\levels\\elora-fergus.png");
    public final static Color[] ELORA_FERGUS_COLOURS = {
            Color.decode("#A82222"),
            Color.decode("#BA6BB0"),
            Color.decode("#DDA15E"),
            Color.decode("#BC6C25"),
            Color.decode("#B8E0DD"),
            Color.decode("#90DD93"),
            Color.decode("#13064C"),
    };
    public final static double[][] ELORA_FERGUS_DATA = {};

    public final static BufferedImage LONDON = ImageUtilities.importImage("src\\images\\levels\\london.png");
    public final static Color[] LONDON_COLOURS = {
            Color.decode("#f69679"),
            Color.decode("#7bcdc8"),
            Color.decode("#7ea7d8"),
            Color.decode("#a187be"),
            Color.decode("#f49ac2"),
            Color.decode("#f6989d"),
            Color.decode("#fdc68a"),
    };
    public final static double[][] LONDON_DATA = {};

    public final static BufferedImage OTTAWA = ImageUtilities.importImage("src\\images\\levels\\ottawa.png");
    public final static Color[] OTTAWA_COLOURS = {
            Color.decode("#3B4C51"),
            Color.decode("#75AA70"),
            Color.decode("#E8A95E"),
            Color.decode("#F67070"),
            Color.decode("#E296D6"),
            Color.decode("#8AEBD7"),
            Color.decode("#CE834F"),
    };
    public final static double[][] OTTAWA_DATA = {};

    public final static BufferedImage STRATFORD = ImageUtilities.importImage("src\\images\\levels\\stratford.png");
    public final static Color[] STRATFORD_COLOURS = {
            Color.decode("#CE2424"),
            Color.decode("#D688D9"),
            Color.decode("#24BE6B"),
            Color.decode("#EFB014"),
            Color.decode("#254D9D"),
            Color.decode("#791097"),
            Color.decode("#975E10"),
    };
    public final static double[][] STRATFORD_DATA = {};

    public final static BufferedImage VICTORIA = ImageUtilities.importImage("src\\images\\levels\\victoria.png");
    public final static Color[] VICTORIA_COLOURS = {
            Color.decode("#22D8BA"),
            Color.decode("#38D822"),
            Color.decode("#B6D822"),
            Color.decode("#D8A122"),
            Color.decode("#D83822"),
            Color.decode("#2269D8"),
            Color.decode("#070970"),
    };
    public final static double[][] VICTORIA_DATA = {};

    public final static BufferedImage WATERLOO = ImageUtilities.importImage("src\\images\\levels\\waterloo.png");
    public final static Color[] WATERLOO_COLOURS = {
            Color.decode("#DCB35D"),
            Color.decode("#8AC696"),
            Color.decode("#B6C649"),
            Color.decode("#D16666"),
            Color.decode("#B67BC7"),
            Color.decode("#EF99BC"),
            Color.decode("#6AC8B7"),
    };
    public final static double[][] WATERLOO_DATA = {};
}
