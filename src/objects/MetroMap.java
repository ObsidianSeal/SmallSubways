/*
 * TITLE: MetroMap
 * AUTHOR: Daniel Zhong, Benjamin Gosselin
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: Map-related information, variables, data, constants, etc.
 */

package objects;

import utilities.ImageUtilities;

import java.awt.image.BufferedImage;

/**
 * Object containing map-specific info and images.
 */
public class MetroMap {
    public final static BufferedImage ELORA_FERGUS = ImageUtilities.importImage("src\\images\\levels\\elora-fergus.png");
    public final static double[][] ELORA_FERGUS_DATA = {};

    public final static BufferedImage LONDON = ImageUtilities.importImage("src\\images\\levels\\london.png");
    public final static double[][] LONDON_DATA = {};

    public final static BufferedImage OTTAWA = ImageUtilities.importImage("src\\images\\levels\\ottawa.png");
    public final static double[][] OTTAWA_DATA = {};

    public final static BufferedImage STRATFORD = ImageUtilities.importImage("src\\images\\levels\\stratford.png");
    public final static double[][] STRATFORD_DATA = {};

    public final static BufferedImage VICTORIA = ImageUtilities.importImage("src\\images\\levels\\victoria.png");
    public final static double[][] VICTORIA_DATA = {};

    public final static BufferedImage WATERLOO = ImageUtilities.importImage("src\\images\\levels\\waterloo.png");
    public final static double[][] WATERLOO_DATA = {};
}
