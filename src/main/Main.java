/*
 * TITLE: Main - SmallSubways
 * AUTHOR: Benjamin Gosselin, Daniel Zhong
 * DATE: Monday, May 27th, 2024
 * DESCRIPTION: The main class for our SmallSubways game.
 */

package main;

import enums.Shape;
import objects.Colour;
import objects.MetroLine;
import objects.MetroMap;
import objects.Station;
import utilities.ImageUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    // objects & object arrays :D
    public static ArrayList<MetroLine> lines = new ArrayList<MetroLine>();
    public static ArrayList<Station> stations = new ArrayList<Station>();

    // timer - for animation, etc.
    static int ticks = 450; // set to 450 to skip studio screen
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
        mainFrame.setIconImage(new ImageIcon("src\\images\\icons\\app.png").getImage());

        // defaults and decorations, then show the window
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(true);
        mainFrame.setVisible(true);

        // graphics, added last to get the correct size
        graphicsPanel = new GraphicsPanel();
        mainFrame.add(graphicsPanel);

        // event listeners
        graphicsPanel.addMouseListener(new MouseListener());
        graphicsPanel.addMouseMotionListener(new MouseMotionListener());
        graphicsPanel.addKeyListener(new KeyboardListener());

        // begin!
        graphicsPanel.requestFocus();
        timer.start();
    }

    /**
     * Inner class for drawing.
     */
    private static class GraphicsPanel extends JPanel {

        // variables
        boolean studioTitleScreenSeen = false;
        int opacity = 0;

        // images
        BufferedImage studioTitleScreen = ImageUtilities.importImage("src\\images\\other\\barking-seal-design.png");
        BufferedImage background = MetroMap.mapEloraAndFergus;

        /**
         * GraphicsPanel constructor.
         */
        GraphicsPanel() {
            this.setBackground(Color.BLACK);

//            for (Shape shape : Shape.values()) {
//                stations.add(new Station((int) (Math.random() * 64), (int) (Math.random() * 36), shape));
//            }

            lines.add(new MetroLine(new ArrayList<Station>(), Colour.PASTEL_BLUE_VIOLET));
//            stations.add(new Station());
//            stations.add(new Station());
//            stations.add(new Station());

            stations.add(new Station(Shape.CIRCLE));
            stations.add(new Station(Shape.TRIANGLE));
            stations.add(new Station(Shape.SQUARE));
            stations.add(new Station(Shape.STAR));
            stations.add(new Station(Shape.PENTAGON));
            stations.add(new Station(Shape.GEM));
            stations.add(new Station(Shape.CROSS));
            stations.add(new Station(Shape.WEDGE));
            stations.add(new Station(Shape.DIAMOND));
            stations.add(new Station(Shape.OVAL));
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

            if (!studioTitleScreenSeen) {
                // fade in...
                if (ticks >= 50 && ticks < 150) {
                    if (opacity < 1000) opacity += 10;
                }

                // fade out...
                if (ticks >= 300) {
                    if (opacity > 0) opacity -= 10;
                }

                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity / 1000f));
                ImageUtilities.drawImageFullScreen(studioTitleScreen);

                if (ticks == 450) {
                    studioTitleScreenSeen = true;
                    ticks = 0;
                }
            } else {
                // level background
                ImageUtilities.drawImageFullScreen(background);

//                if (ticks % 100 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());

                // lines
                for (MetroLine line : lines) {
                    line.draw();
                }

                // stations
                for (Station station : stations) {
                    if (station.isSelected()) station.highlight();
                    station.draw();
                }
            }
        }
    }

    /**
     * Listener for mouse events.
     */
    private static class MouseListener extends MouseAdapter {

        /**
         * Event handler for when a mouse button is clicked.
         * @param e The event to be processed.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            // DEBUG: station selecting
            for (Station station : stations) {
                if (Math.abs(station.getX() - e.getX()) < 30 && Math.abs(station.getY() - e.getY()) < 30) {
                    Main.lines.getFirst().addStation(station);
                    station.setSelected(true);
                } else {
                    station.setSelected(false);
                }
            }
        }

    }

    /**
     * Listener for mouse movement events.
     */
    private static class MouseMotionListener extends MouseMotionAdapter {
        // unused, for now
    }

    /**
     * Listener for keyboard events.
     */
    private static class KeyboardListener extends KeyAdapter {

        /**
         * Event handler for when a key is pressed down.
         * @param e The event to be processed.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode()); // DEBUG

            switch (e.getKeyCode()) {
                case (KeyEvent.VK_ESCAPE) -> System.exit(0);
            }
        }

    }

}
