/*
 * TITLE: Colour
 * AUTHOR: Benjamin Gosselin
 * DATE: Wednesday, May 29th, 2024
 * DESCRIPTION: Colour constants from HEX codes from various sources.
 */

package objects;

import java.awt.*;

/**
 * Colours for easy grabbing.
 */
public class Colour {
    // "Grayscale" from Photoshop
    public final static Color GREY_10 = Color.decode("#ebebeb");
    public final static Color GREY_15 = Color.decode("#e1e1e1");
    public final static Color GREY_20 = Color.decode("#d7d7d7");
    public final static Color GREY_25 = Color.decode("#cccccc");
    public final static Color GREY_30 = Color.decode("#c2c2c2");
    public final static Color GREY_35 = Color.decode("#b7b7b7");
    public final static Color GREY_40 = Color.decode("#acacac");
    public final static Color GREY_45 = Color.decode("#a1a1a1");
    public final static Color GREY_50 = Color.decode("#959595");
    public final static Color GREY_55 = Color.decode("#898989");
    public final static Color GREY_60 = Color.decode("#7d7d7d");
    public final static Color GREY_65 = Color.decode("#707070");
    public final static Color GREY_70 = Color.decode("#636363");
    public final static Color GREY_75 = Color.decode("#555555");
    public final static Color GREY_80 = Color.decode("#464646");
    public final static Color GREY_85 = Color.decode("#363636");
    public final static Color GREY_90 = Color.decode("#252525");
    public final static Color GREY_95 = Color.decode("#111111");

    // "Pastel" from Photoshop
    public final static Color PASTEL_RED = Color.decode("#f69679");
    public final static Color PASTEL_RED_ORANGE = Color.decode("#f9ad81");
    public final static Color PASTEL_YELLOW_ORANGE = Color.decode("#fdc68a");
    public final static Color PASTEL_YELLOW = Color.decode("#fff79a");
    public final static Color PASTEL_PEA_GREEN = Color.decode("#c4df9b");
    public final static Color PASTEL_YELLOW_GREEN = Color.decode("#a2d39c");
    public final static Color PASTEL_GREEN = Color.decode("#82ca9d");
    public final static Color PASTEL_GREEN_CYAN = Color.decode("#7bcdc8");
    public final static Color PASTEL_CYAN = Color.decode("#6ecff6");
    public final static Color PASTEL_CYAN_BLUE = Color.decode("#7ea7d8");
    public final static Color PASTEL_BLUE = Color.decode("#8493ca");
    public final static Color PASTEL_BLUE_VIOLET = Color.decode("#8882be");
    public final static Color PASTEL_VIOLET = Color.decode("#a187be");
    public final static Color PASTEL_VIOLET_MAGENTA = Color.decode("#bc8dbf");
    public final static Color PASTEL_MAGENTA = Color.decode("#f49ac2");
    public final static Color PASTEL_MAGENTA_RED = Color.decode("#f6989d");

    // "Light" from Photoshop
    public final static Color LIGHT_RED = Color.decode("#f26c4f");
    public final static Color LIGHT_RED_ORANGE = Color.decode("#f68e56");
    public final static Color LIGHT_YELLOW_ORANGE = Color.decode("#fbaf5d");
    public final static Color LIGHT_YELLOW = Color.decode("#fff568");
    public final static Color LIGHT_PEA_GREEN = Color.decode("#acd373");
    public final static Color LIGHT_YELLOW_GREEN = Color.decode("#7cc576");
    public final static Color LIGHT_GREEN = Color.decode("#3cb878");
    public final static Color LIGHT_GREEN_CYAN = Color.decode("#1cbbb4");
    public final static Color LIGHT_CYAN = Color.decode("#00bff3");
    public final static Color LIGHT_CYAN_BLUE = Color.decode("#448ccb");
    public final static Color LIGHT_BLUE = Color.decode("#5674b9");
    public final static Color LIGHT_BLUE_VIOLET = Color.decode("#605ca8");
    public final static Color LIGHT_VIOLET = Color.decode("#8560a8");
    public final static Color LIGHT_VIOLET_MAGENTA = Color.decode("#a864a8");
    public final static Color LIGHT_MAGENTA = Color.decode("#f06eaa");
    public final static Color LIGHT_MAGENTA_RED = Color.decode("#f26d7d");

    public static Color atOpacity(Color colour, int opacity) {
        return (new Color(colour.getRed(), colour.getGreen(), colour.getBlue(), opacity));
    }

}
