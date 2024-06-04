/*
 * TITLE: Main - SmallSubways
 * AUTHOR: Benjamin Gosselin, Daniel Zhong
 * DATE: Monday, May 27th, 2024
 * DESCRIPTION: The main class for our SmallSubways game.
 */

package main;

import enums.Map;
import enums.Shape;
import objects.*;
import utilities.ImageUtilities;
import utilities.MapUtilities;

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

    // the map!
    public static MetroMap map = new MetroMap(Map.STRATFORD);

    // grid square type constants
    public static final double MARGIN = -2.0;
    public static final double TAKEN = -1.0;
    public static final double WATER = 0.0;
    public static final double COUNTRY = 0.5;
    public static final double CITY = 1.0;
    public static int openCount = 80 * 45;

    // objects & object arrays :D
    public static ArrayList<MetroLine> lines = new ArrayList<MetroLine>();
    public static ArrayList<Station> stations = new ArrayList<Station>();
    public static double[][] grid = new double[45][80];

    // variables
    public static double gridSize;
    static int mouseX;
    static int mouseY;
    static boolean shiftHeld = false;
    static boolean controlHeld = false;
    static int currentLine;

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
        double studioTitleScreenScale = 1;
        int studioTitleScreenOpacity = 0;

        // images
        BufferedImage studioTitleScreen = ImageUtilities.importImage("src\\images\\other\\barking-seal-design.png");
        BufferedImage background = map.getMap();

        /**
         * GraphicsPanel constructor.
         */
        GraphicsPanel() {
            // important initialization
            this.setBackground(Color.BLACK);
            gridSize = mainFrame.getWidth() / 80.0;

            // set up grid squares
            MapUtilities.initializeGrid(); // set all to default ("COUNTRY")
            MapUtilities.disallowEdge();
            MapUtilities.disallowMenuAreas();
            MapUtilities.disallowWater();

            // add initial lines
            lines.add(new MetroLine(map.getColours()[0]));
            lines.add(new MetroLine(map.getColours()[1]));
            lines.add(new MetroLine(map.getColours()[2]));
//            lines.add(new MetroLine(map.getColours()[3]));
//            lines.add(new MetroLine(map.getColours()[4]));
//            lines.add(new MetroLine(map.getColours()[5]));
//            lines.add(new MetroLine(map.getColours()[6]));

            // add initial stations
            stations.add(new Station());
            stations.add(new Station());
            stations.add(new Station());
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
                    if (studioTitleScreenOpacity < 1000) studioTitleScreenOpacity += 10;
                }

                // fade out...
                if (ticks >= 300) {
                    if (studioTitleScreenOpacity > 0) studioTitleScreenOpacity -= 10;
                }

                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, studioTitleScreenOpacity / 1000f));
                ImageUtilities.drawImageFullScreen(studioTitleScreen);

//                studioTitleScreenScale += 0.0005;
//                g2D.drawImage(studioTitleScreen, (int) ((Main.mainFrame.getWidth() * studioTitleScreenScale - Main.mainFrame.getWidth()) / -2.0), (int) ((Main.mainFrame.getHeight() * studioTitleScreenScale - Main.mainFrame.getHeight()) / -2.0), (int) (Main.mainFrame.getWidth() * studioTitleScreenScale), (int) (Main.mainFrame.getHeight() * studioTitleScreenScale), null);

                if (ticks == 450) {
                    studioTitleScreenSeen = true;
                    ticks = 0;
                }
            } else {
                // level background
                ImageUtilities.drawImageFullScreen(background);

                // spawn stations until no more can be spawned
                if (openCount > 1) {
                    if (ticks % 150 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());
//                    if (ticks % 15 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());
//                    if (ticks % 15 == 0) stations.add(new Station());
                }

                // spawn passengers
//                if (ticks % 150 == 0) System.out.println((int) (Math.random() * 15) == 0);
                if (ticks % 75 == 0 && (int) (Math.random() * 15) == 0) stations.get((int) (Math.random() * stations.size())).getPassengers().add(new Passenger());
//                if (ticks % 15 == 0 && (int) (Math.random() * 15) == 0) stations.get((int) (Math.random() * stations.size())).getPassengers().add(new Passenger());
//                if (ticks % 15 == 0) stations.get((int) (Math.random() * stations.size())).getPassengers().add(new Passenger());

                // EDIT/DEBUG MODE!!
                if (controlHeld) {
                    // grid square types
                    for (int i = 0; i < 45; i++) {
                        for (int j = 0; j < 80; j++) {
                            if (grid[i][j] == WATER) g2D.setColor(Colour.LIGHT_BLUE);
                            if (grid[i][j] == COUNTRY) g2D.setColor(Colour.LIGHT_YELLOW_GREEN);
                            if (grid[i][j] == TAKEN) g2D.setColor(Colour.LIGHT_RED);
                            if (grid[i][j] == MARGIN) g2D.setColor(Colour.LIGHT_YELLOW);

                            if (grid[i][j] != CITY) g2D.fillRect((int) (j * (gridSize)), i * (mainFrame.getHeight() / 45), (int) gridSize, mainFrame.getHeight() / 45);
                        }
                    }

                    // grid
                    g2D.setColor(Colour.LIGHT_VIOLET_MAGENTA);
                    for (int i = 0; i < 80; i++) g2D.drawLine((int) (i * gridSize), 0, (int) (i * gridSize), getHeight());
                    for (int i = 0; i < 45; i++) g2D.drawLine(0, (int) (i * gridSize), getWidth(), (int) (i * gridSize));
                }

                // lines
                for (MetroLine line : lines) {
                    line.draw();
                }

                // passengers
                for (Station station : stations) {
                    station.drawPassengers();
                }

                // stations
                for (Station station : stations) {
                    if (station.isSelected()) station.highlight();
                    station.draw();
                }

                // line selection menu
                for (int i = 6; i >= 0; i--) {
                    int size;

                    if (lines.size() > 6 - i) {
                        Main.g2D.setColor(lines.get(6 - i).getColour());
                        size = (int) (gridSize * 2.5);
                        if (6 - i == currentLine) size = (int) (gridSize * 3);
                    } else {
                        Main.g2D.setColor(Colour.GREY_25);
                        size = (int) (gridSize * 1.5);
                    }

                    Main.g2D.fillOval((int) (mainFrame.getWidth() - gridSize * 3 - i * gridSize * 4 - size / 2), (int) (mainFrame.getHeight() - gridSize * 3 - size / 2), size, size);
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
            // left click
            if (e.getButton() == 1) {
                // add/remove station to/from line
                for (Station station : stations) {
                    if (Math.abs(station.getX() - e.getX()) < (mainFrame.getWidth() / 64f) && Math.abs(station.getY() - e.getY()) < (mainFrame.getWidth() / 64f)) {
                        if (!lines.get(currentLine).getStations().contains(station)) {
                            station.setDiagonal(shiftHeld);
                            lines.get(currentLine).addStation(station);
                        } else {
                            lines.get(currentLine).removeStation(station);
                        }
                    }
                }
            }
        }

    }

    /**
     * Listener for mouse movement events.
     */
    private static class MouseMotionListener extends MouseMotionAdapter {

        /**
         * Event handler for when the mouse is moved.
         * @param e The event to be processed.
         */
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            // station hover
            for (Station station : stations) {
                station.setSelected(Math.abs(station.getX() - e.getX()) < (mainFrame.getWidth() / 64f) && Math.abs(station.getY() - e.getY()) < (mainFrame.getWidth() / 64f));
            }
        }

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
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_ESCAPE) -> System.exit(0);
                case (KeyEvent.VK_SHIFT) -> shiftHeld = true;
                case (KeyEvent.VK_CONTROL) -> controlHeld = true;

                // temporary line selection
                case (KeyEvent.VK_1) -> currentLine = 0;
                case (KeyEvent.VK_2) -> currentLine = 1;
                case (KeyEvent.VK_3) -> currentLine = 2;
                case (KeyEvent.VK_4) -> currentLine = 3;
                case (KeyEvent.VK_5) -> currentLine = 4;
                case (KeyEvent.VK_6) -> currentLine = 5;
                case (KeyEvent.VK_7) -> currentLine = 6;
            }

            // EDIT/DEBUG MODE!!
            if (controlHeld) {
                if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {
                    stations.add(new Station((int) (mouseX / (gridSize)), mouseY / (mainFrame.getHeight() / 45), Shape.values()[e.getKeyCode() - KeyEvent.VK_0]));
                }
            }

        }

        /**
         * Event handler for when a key is released.
         * @param e The event to be processed.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_SHIFT) -> shiftHeld = false;
                case (KeyEvent.VK_CONTROL) -> controlHeld = false;
            }
        }

    }

}
