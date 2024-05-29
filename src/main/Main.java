/*
 * TITLE: Main - SmallSubways
 * AUTHOR: Benjamin Gosselin
 * DATE: Monday, May 27th, 2024
 * DESCRIPTION: The main class for our SmallSubways game.
 */

package main;

import enums.Shape;
import objects.Station;
import utilities.ImageUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Main everything - this is where it all begins.
 */
public class Main {

    /**
     * The main method - runs the program.
     * @param args args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }

    // the window
    public static JFrame mainFrame = new JFrame();

    // graphics!
    public static Graphics2D g2D;
    static GraphicsPanel graphicsPanel;

    // timer - for animation, etc.
    static int ticks = 500;
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ticks++;
            graphicsPanel.repaint();
        }
    });

    /**
     * Constructor - where the main magic happens.
     */
    Main() {
        // title, icon
        mainFrame.setTitle("SmallSubways");
        // we need an icon: mainFrame.setIconImage(new ImageIcon("").getImage());

        // defaults and decorations, then show the window
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(true);
        mainFrame.setVisible(true);

        // graphics, added last to get the correct size
        graphicsPanel = new GraphicsPanel();
        mainFrame.add(graphicsPanel);

        // begin!
        timer.start();
    }

    /**
     * Inner class for drawing.
     */
    private static class GraphicsPanel extends JPanel {

        // variables
        int opacity = 0;
        ArrayList<Station> stations = new ArrayList<Station>();

        // images
        BufferedImage studioTitleScreen = ImageUtilities.importImage("src\\images\\other\\barking-seal-design.png");
        BufferedImage background = ImageUtilities.importImage("src\\images\\levels\\victoria.png");

        /**
         * GraphicsPanel constructor.
         */
        GraphicsPanel() {
            this.setBackground(Color.BLACK);

            for (Shape shape : Shape.values()) {
                stations.add(new Station((int) (Math.random() * 64), (int) (Math.random() * 36), shape));
            }
        }

        /**
         * This is where the drawing occurs.
         * @param g The graphics object - for drawing things.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g2D = (Graphics2D) g;
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // fade in...
            if (ticks >= 50 && ticks < 350) {
                if (opacity < 1000) opacity += 10;

                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity / 1000f));
                ImageUtilities.drawImageFullScreen(studioTitleScreen);
            }

            // fade out...
            if (ticks >= 350 && ticks < 500) {
                if (opacity > 0) opacity -= 10;

                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity / 1000f));
                ImageUtilities.drawImageFullScreen(studioTitleScreen);
            }

            // TEST: draw background & stations
            if (ticks >= 500) {
                ImageUtilities.drawImageFullScreen(background);

                for (Station station : stations) {
                    station.draw();
                }
            }
        }

    }

}
