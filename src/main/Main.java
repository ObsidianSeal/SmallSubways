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
    public static MetroLine[] lines = {null, null, null, null, null, null, null};
    public static ArrayList<Station> stations = new ArrayList<Station>();
    public static int[] resources = new int[4];
    public static double[][] grid = new double[45][80];

    // variables
    public static double gridSize;
    static int mouseX, mouseY;
    static int gridX, gridY;
    static boolean shiftHeld = false;
    static boolean controlHeld = false;
    static int currentLine;
    static int circleHover = -1;

    // timer - for animation, etc.
    static int ticks = 450; // set to 450 to skip studio screen
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ticks++;
            graphicsPanel.repaint();
        }
    });

    // fonts
    static Font robotoMono24 = new Font("Roboto Mono", Font.PLAIN, 24);

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
        int studioTitleScreenOpacity = 0;

        // images
        BufferedImage studioTitleScreen = ImageUtilities.importImage("src\\images\\other\\barking-seal-design.png");
        BufferedImage background = map.getMap();
        BufferedImage lineIcon = ImageUtilities.importImage("src\\images\\icons\\line.png");
        BufferedImage tunnelIcon = ImageUtilities.importImage("src\\images\\icons\\tunnel.png");
        BufferedImage bridgeIcon = ImageUtilities.importImage("src\\images\\icons\\bridge.png");
        BufferedImage locomotiveIcon = ImageUtilities.importImage("src\\images\\icons\\locomotive.png");
        BufferedImage carriageIcon = ImageUtilities.importImage("src\\images\\icons\\carriage.png");
        BufferedImage interchangeIcon = ImageUtilities.importImage("src\\images\\icons\\interchange.png");

        /**
         * GraphicsPanel constructor.
         */
        GraphicsPanel() {
            // important initialization
            this.setBackground(Color.BLACK);
            gridSize = mainFrame.getWidth() / 80.0;

            // set up grid squares
            MapUtilities.initializeGrid(); // set all to default ("COUNTRY")
            MapUtilities.disallowWater();
            MapUtilities.disallowEdge();
            MapUtilities.disallowMenuAreas();

            // add initial lines
            lines[0] = new MetroLine(map.getColours()[0]);
            lines[1] = new MetroLine(map.getColours()[1]);
            lines[2] = new MetroLine(map.getColours()[2]);
//            lines[3] = new MetroLine(map.getColours()[3]);
//            lines[4] = new MetroLine(map.getColours()[4]);
//            lines[5] = new MetroLine(map.getColours()[5]);
//            lines[6] = new MetroLine(map.getColours()[6]);

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
            g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // smooth images; quality & speed tradeoff

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

                            if (grid[i][j] != CITY) g2D.fillRect((int) (j * (gridSize)), (int) (i * gridSize), (int) gridSize, mainFrame.getHeight() / 45);
                        }
                    }

                    // grid
                    g2D.setColor(Colour.LIGHT_VIOLET_MAGENTA);
                    for (int i = 0; i < 80; i++) g2D.drawLine((int) (i * gridSize), 0, (int) (i * gridSize), getHeight());
                    for (int i = 0; i < 45; i++) g2D.drawLine(0, (int) (i * gridSize), getWidth(), (int) (i * gridSize));
                }

                // lines
                for (MetroLine line : lines) {
                    if (line != null) line.draw();
                }

                // passengers
                for (Station station : stations) {
                    station.drawPassengers();
                }

                // stations
                for (Station station : stations) {
                    if (station.isSelected()) {
                        if (circleHover >= 0) station.highlight(lines[circleHover].getColour());
                        else station.highlight(lines[currentLine].getColour());
                    }
                    station.draw();
                }

                // line selection menu
                for (int i = 0; i < 7; i++) {
                    double size;

                    if (lines[i] != null) {
                        g2D.setColor(lines[i].getColour());

                        if (i == currentLine) {
                            if (!lines[i].getStations().isEmpty()) size = gridSize * 2.5;
                            else size = gridSize * 1.6;
                        } else {
                            if (!lines[i].getStations().isEmpty()) size = gridSize * 2.1;
                            else size = gridSize * 1.3;
                        }
                    } else {
                        g2D.setColor(map.getColours()[12]);
                        size = gridSize * 1.3;
                    }

                    g2D.fillOval((int) (mainFrame.getWidth() - gridSize * 3 - (6 - i) * gridSize * 3 - size / 2), (int) (mainFrame.getHeight() - gridSize * 3 - size / 2), (int) size, (int) size);
                }

                // resource icons
                for (int i = 0; i < 4; i++) {
                    double size = gridSize * 2.5;
                    int xPosition = (int) (gridSize * 3 + i * gridSize * 4 - size / 2);
                    int yPosition = (int) (mainFrame.getHeight() - gridSize * 3 - size / 2);
                    BufferedImage icon = null;

                    switch (i) {
                        case 0 -> icon = locomotiveIcon;
                        case 1 -> icon = carriageIcon;
                        case 2 -> icon = interchangeIcon;
                        case 3 -> {
                            if (map.getWaterTravelType()) icon = tunnelIcon;
                            else icon = bridgeIcon;
                        }
                    }
                    ImageUtilities.drawImage(icon, xPosition, yPosition, (int) size, (int) size);

                    g2D.setColor(Color.BLACK); g2D.setFont(robotoMono24);
                    g2D.drawString(String.valueOf(resources[i]), (int) (xPosition + size), (int) (yPosition + gridSize / 4));
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
                    if (station.getGridX() == gridX && station.getGridY() == gridY) {
                        if (!lines[currentLine].getStations().contains(station)) {
                            station.setDiagonal(lines[currentLine], shiftHeld);
                            lines[currentLine].addStation(station);
                        } else {
                            lines[currentLine].removeStation(station);
                        }
                    }
                }

                // line selection
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < 2; k++) {
                            if (i * 3 + 58 + j == gridX && k + 41 == gridY) {
                                if (lines[i] != null) currentLine = i;
                                break;
                            }
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

            gridX = (int) (mouseX / gridSize);
            gridY = (int) (mouseY / gridSize);

            // hover
            if (gridX >= 54 && gridY >= 38) {
                // line circle hover
                circleHover = -1;
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < 2; k++) {
                            if (i * 3 + 58 + j == gridX && k + 41 == gridY) {
                                if (lines[i] != null) circleHover = i;
                                break;
                            }
                        }
                    }
                }
                for (Station station : stations) {
                    station.setSelected(false);
                }
                for (int i = 0; i < 7; i++) {
                    if (i == circleHover) {
                        for (Station station : lines[i].getStations()) {
                            station.setSelected(true);
                        }
                    }
                }
            } else {
                // station hover
                for (Station station : stations) {
                    station.setSelected(station.getGridX() == gridX && station.getGridY() == gridY);
                }
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
            }

            // line selection
            if (e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_7) {
                if (lines[e.getKeyCode() - KeyEvent.VK_1] != null) currentLine = e.getKeyCode() - KeyEvent.VK_1;
            }

            // EDIT/DEBUG MODE!!
            if (controlHeld) {
                if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {
                    stations.add(new Station(gridX, gridY, Shape.values()[e.getKeyCode() - KeyEvent.VK_0]));
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
