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
    public final static BufferedImage mapEloraAndFergus = ImageUtilities.importImage("src\\images\\levels\\elora-fergus.png");
    public final static double[][] dataEloraAndFergus = {};

    public final static BufferedImage mapLondon = ImageUtilities.importImage("src\\images\\levels\\london.png");
    public final static double[][] dataLondon = {};

    public final static BufferedImage mapOttawa = ImageUtilities.importImage("src\\images\\levels\\ottawa.png");
    public final static double[][] dataOttawa = {};

    public final static BufferedImage mapStratford = ImageUtilities.importImage("src\\images\\levels\\stratford.png");
    public final static double[][] dataStratford = {};

    public final static BufferedImage mapVictoria = ImageUtilities.importImage("src\\images\\levels\\victoria.png");
    public final static double[][] dataVictoria = {};

    public final static BufferedImage mapWaterloo = ImageUtilities.importImage("src\\images\\levels\\waterloo.png");
    public final static double[][] dataWaterloo = {};
}
