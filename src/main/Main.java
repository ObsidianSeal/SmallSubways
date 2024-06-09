/*
 * TITLE: Main - SmallSubways
 * AUTHOR: Benjamin Gosselin, Daniel Zhong
 * DATE: Monday, May 27th, 2024
 * DESCRIPTION: The main class for our SmallSubways game.
 */

package main;

import enums.Map;
import enums.Screen;
import objects.*;
import utilities.FontUtilities;
import utilities.ImageUtilities;
import utilities.MapUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
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

    // grid square type constants
    public static final double MARGIN = -2.0;
    public static final double TAKEN = -1.0;
    public static final double WATER = 0.0;
    public static final double COUNTRY = 0.5;
    public static final double CITY = 1.0;
    public static int openCount = 80 * 45;

    // objects & object arrays :D
    public static MetroMap map;
    static Image mapImage;
    public static MetroLine[] lines;
    public static ArrayList<Station> stations = new ArrayList<Station>();
    static String[] days = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    public static Screen screenState = Screen.STUDIO_TITLE;

    // variables
    public static double gridSize;
    static int mouseX, mouseY;
    static int gridX, gridY;
    static boolean shiftHeld = false;
    static boolean controlHeld = false;
    static boolean altHeld = false;
    static int currentLine;
    static int circleHover = -1;
    public static int[] resources = new int[4];
    public static double[][] grid = new double[45][80];
    static int points;
    static int levelSelectIndex;

    // timer - for animation, etc.
    static int ticks = 450; // set to 450 to skip studio screen
    static int tickRate = 1; // tick speed multiplier
    static int regularTickRate = tickRate;
//    static long pTime = System.nanoTime(); // for delay debugging
    static Screen pScreen;
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
//            long time1 = System.nanoTime();

            if (screenState != pScreen) ticks = 0;
            pScreen = screenState;

            graphicsPanel.repaint();
            ticks += tickRate;

//            long time2 = System.nanoTime();
//
//            System.out.println(((time1 - pTime) / 1_000_000.0) + "ms, " + ((time2 - time1) / 1_000_000.0) + "ms");
//            pTime = time1;
        }
    });

    // fonts
    static Font robotoMonoRegular24;
    static Font robotoSerifMedium48;
    static Font robotoSerifLight36;

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
     * After a map is chosen from level select, set up the necessary things.
     * @param level The chosen map.
     * @return The map's background.
     */
    private static Image mapSetup(Map level) {
        map = new MetroMap(level);

        // reset level data
        lines = new MetroLine[]{null, null, null, null, null, null, null};
        stations.clear();
        currentLine = 0;
        points = 0;

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

        return ImageUtilities.resizeFullScreen(map.getMap());
    }

    /**
     * Inner class for drawing.
     */
    private static class GraphicsPanel extends JPanel {
        // variables
        int studioTitleScreenOpacity = 0;

        // images
        Image studioTitleScreen = ImageUtilities.importImage("src\\images\\other\\barking-seal-design.png");
        Image mainMenu = ImageUtilities.importImage("src\\images\\other\\main-menu.png");

        Image lineIcon = ImageUtilities.importImage("src\\images\\icons\\line.png");
        Image tunnelIcon = ImageUtilities.importImage("src\\images\\icons\\tunnel.png");
        Image bridgeIcon = ImageUtilities.importImage("src\\images\\icons\\bridge.png");
        Image locomotiveIcon = ImageUtilities.importImage("src\\images\\icons\\locomotive.png");
        Image carriageIcon = ImageUtilities.importImage("src\\images\\icons\\carriage.png");
        Image interchangeIcon = ImageUtilities.importImage("src\\images\\icons\\interchange.png");

        Image person = ImageUtilities.importImage("src\\images\\icons\\person.png");

        Image[] mapThumbnails = {
                ImageUtilities.importImage("src\\images\\thumbnails\\elora-fergus.png"),
                ImageUtilities.importImage("src\\images\\thumbnails\\london.png"),
                ImageUtilities.importImage("src\\images\\thumbnails\\ottawa.png"),
                ImageUtilities.importImage("src\\images\\thumbnails\\stratford.png"),
                ImageUtilities.importImage("src\\images\\thumbnails\\victoria.png"),
                ImageUtilities.importImage("src\\images\\thumbnails\\waterloo.png")
        };

        /**
         * GraphicsPanel constructor.
         */
        GraphicsPanel() {
            // important initialization
            this.setBackground(Color.BLACK);
            gridSize = mainFrame.getWidth() / 80.0;

            // fonts
            robotoMonoRegular24 = FontUtilities.importFont("src\\fonts\\RobotoMono-Regular.ttf", (float) (gridSize));
            robotoSerifMedium48 = FontUtilities.importFont("src\\fonts\\RobotoSerif-Medium.ttf", (float) (gridSize * 2));
            robotoSerifLight36 = FontUtilities.importFont("src\\fonts\\RobotoSerif-Light.ttf", (float) (gridSize * 1.5));

            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(robotoMonoRegular24);
            graphicsEnvironment.registerFont(robotoSerifMedium48);
            graphicsEnvironment.registerFont(robotoSerifLight36);

            // resize images
            studioTitleScreen = ImageUtilities.resizeFullScreen(studioTitleScreen);
            mainMenu = ImageUtilities.resizeFullScreen(mainMenu);

            lineIcon = ImageUtilities.rezise(lineIcon, (int) (gridSize * 2.5), (int) (gridSize * 2.5));
            tunnelIcon = ImageUtilities.rezise(tunnelIcon, (int) (gridSize * 2.5), (int) (gridSize * 2.5));
            bridgeIcon = ImageUtilities.rezise(bridgeIcon, (int) (gridSize * 2.5), (int) (gridSize * 2.5));
            locomotiveIcon = ImageUtilities.rezise(locomotiveIcon, (int) (gridSize * 2.5), (int) (gridSize * 2.5));
            carriageIcon = ImageUtilities.rezise(carriageIcon, (int) (gridSize * 2.5), (int) (gridSize * 2.5));
            interchangeIcon = ImageUtilities.rezise(interchangeIcon, (int) (gridSize * 2.5), (int) (gridSize * 2.5));

            person = ImageUtilities.rezise(person, (int) (gridSize * 2.5), (int) (gridSize * 2.5));

            for (int i = 0; i < mapThumbnails.length; i++) {
                mapThumbnails[i] = ImageUtilities.rezise(mapThumbnails[i], (int) (Main.gridSize * 16), (int) (Main.gridSize * 10));
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

            if (screenState == Screen.STUDIO_TITLE) {
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

                if (ticks == 450) screenState = Screen.MAIN_MENU;
            }

            if (screenState == Screen.MAIN_MENU) {
                ImageUtilities.drawImageFullScreen(mainMenu);
            }

            if (screenState == Screen.LEVEL_SELECT) {
                // background
                g2D.setColor(Colour.GREY_95);
                g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

                // back arrow
                g2D.setColor(Colour.GREY_30); g2D.setStroke(new BasicStroke((float) (gridSize / 2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 7), (int) (gridSize * 3));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 4), (int) (gridSize * 2));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 4), (int) (gridSize * 4));

                // level boxes
                for (int i = 0; i < 3; i++) {
                    // box & map thumbnail
                    g2D.setColor(Colour.GREY_90);
                    g2D.fillRect((int) (gridSize * 11 + gridSize * 21 * i), (int) (gridSize * 10), (int) (gridSize * 16), (int) (gridSize * 25));
                    ImageUtilities.drawImage(mapThumbnails[levelSelectIndex + i], (int) (gridSize * 11 + gridSize * 21 * i), (int) (gridSize * 10));

                    // arrow
                    g2D.setColor(Colour.GREY_50);
                    g2D.drawLine((int) (gridSize * 21 + gridSize * 21 * i), (int) (gridSize * 32),(int) (gridSize * 25 + gridSize * 21 * i), (int) (gridSize * 32));
                    g2D.drawLine((int) (gridSize * 24 + gridSize * 21 * i), (int) (gridSize * 31),(int) (gridSize * 25 + gridSize * 21 * i), (int) (gridSize * 32));
                    g2D.drawLine((int) (gridSize * 24 + gridSize * 21 * i), (int) (gridSize * 33),(int) (gridSize * 25 + gridSize * 21 * i), (int) (gridSize * 32));

                    // text
                    g2D.setColor(Color.WHITE);
                    g2D.setFont(robotoSerifMedium48);
                    g2D.drawString(MetroMap.cities[levelSelectIndex + i], (int) (gridSize * 12 + gridSize * 21 * i), (int) (gridSize * 23));
                    g2D.setFont(robotoSerifLight36);
                    g2D.drawString(MetroMap.countries[levelSelectIndex + i], (int) (gridSize * 12 + gridSize * 21 * i), (int) (gridSize * 25));
                }

                // left & right arrows
                g2D.setColor(Colour.GREY_30);
                if (levelSelectIndex > 0) {
                    g2D.drawLine((int) (gridSize * 4.5), (int) (gridSize * 22.5),(int) (gridSize * 6.5), (int) (gridSize * 20.5));
                    g2D.drawLine((int) (gridSize * 4.5), (int) (gridSize * 22.5),(int) (gridSize * 6.5), (int) (gridSize * 24.5));
                }
                if (levelSelectIndex < MetroMap.cities.length - 3) {
                    g2D.drawLine((int) (gridSize * 73.5), (int) (gridSize * 20.5),(int) (gridSize * 75.5), (int) (gridSize * 22.5));
                    g2D.drawLine((int) (gridSize * 73.5), (int) (gridSize * 24.5),(int) (gridSize * 75.5), (int) (gridSize * 22.5));
                }
            }

            if (screenState == Screen.SETTINGS) {
                // background
                g2D.setColor(Colour.GREY_95);
                g2D.fillRect(0, 0, this.getWidth(), this.getHeight());

                // back arrow
                g2D.setColor(Colour.GREY_30); g2D.setStroke(new BasicStroke((float) (gridSize / 2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 7), (int) (gridSize * 3));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 4), (int) (gridSize * 2));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 4), (int) (gridSize * 4));

                // text
                g2D.setColor(Color.WHITE); g2D.setFont(robotoSerifLight36);
                g2D.drawString("Just kidding, there are no settings.", (int) (gridSize * 5), (int) (gridSize * 8));
            }

            if (screenState == Screen.GAME) {
                // level background
                ImageUtilities.drawImageFullScreen(mapImage);

                // spawn stations until no more can be spawned TODO: move to StationSpawner
                if (openCount > 1) {
                    if (ticks % 120 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());
//                    if (ticks % 15 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());
//                    if (ticks % 15 == 0) stations.add(new Station());
                }

                // spawn passengers TODO: move to PassengerSpawner
                if (ticks % 60 == 0 && (int) (Math.random() * 15) == 0) stations.get((int) (Math.random() * stations.size())).getPassengers().add(new Passenger());
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
                    Image icon = null;

                    switch (i) {
                        case 0 -> icon = locomotiveIcon;
                        case 1 -> icon = carriageIcon;
                        case 2 -> icon = interchangeIcon;
                        case 3 -> {
                            if (map.getWaterTravelType()) icon = tunnelIcon;
                            else icon = bridgeIcon;
                        }
                    }
                    ImageUtilities.drawImage(icon, xPosition, yPosition);

                    g2D.setColor(Color.BLACK); g2D.setFont(robotoMonoRegular24);
                    g2D.drawString(String.valueOf(resources[i]), (int) (xPosition + size), (int) (yPosition + gridSize / 4));
                }

                // clock & day of week
                if (ticks % 1440 > 720) g2D.setColor(Color.BLACK);
                else g2D.setColor(Color.WHITE);
                g2D.fillOval((int) (mainFrame.getWidth() - gridSize * 4.5), (int) (gridSize * 1.5), (int) (gridSize * 3), (int) (gridSize * 3));
                if (ticks % 1440 > 720) g2D.setColor(Color.WHITE);
                else g2D.setColor(Color.BLACK);
                for (int i = 0; i < 12; i++) {
                    if (i % 3 == 0) {
                        g2D.setStroke(new BasicStroke((float) (gridSize / 9.6), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                        g2D.drawLine((int) (mainFrame.getWidth() - gridSize * 3 + (gridSize * 0.85) * Math.cos(Math.toRadians(30 * i))), (int) (gridSize * 3 + (gridSize * 0.85) * Math.sin(Math.toRadians(30 * i))), (int) (mainFrame.getWidth() - gridSize * 3 + (gridSize * 1.25) * Math.cos(Math.toRadians(30 * i))), (int) (gridSize * 3 + (gridSize * 1.25) * Math.sin(Math.toRadians(30 * i))));
                    } else {
                        g2D.setStroke(new BasicStroke((float) (gridSize / 16), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                        g2D.drawLine((int) (mainFrame.getWidth() - gridSize * 3 + (gridSize * 0.9) * Math.cos(Math.toRadians(30 * i))), (int) (gridSize * 3 + (gridSize * 0.9) * Math.sin(Math.toRadians(30 * i))), (int) (mainFrame.getWidth() - gridSize * 3 + (gridSize * 1.25) * Math.cos(Math.toRadians(30 * i))), (int) (gridSize * 3 + (gridSize * 1.25) * Math.sin(Math.toRadians(30 * i))));
                    }
                }
                g2D.setStroke(new BasicStroke((float) (gridSize / 6), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
                GeneralPath clockHand = new GeneralPath();
                clockHand.moveTo(mainFrame.getWidth() - gridSize * 3, gridSize * 3);
                clockHand.lineTo(mainFrame.getWidth() - gridSize * 3 + (gridSize * 0.7) * Math.cos(Math.toRadians(ticks / 2.0 - 90)), gridSize * 3 + (gridSize * 0.7) * Math.sin(Math.toRadians(ticks / 2.0 - 90)));
                g2D.draw(clockHand);
                g2D.setColor(Color.BLACK);
                g2D.drawString(days[((int) (ticks / 1440)) % 7], (float) (mainFrame.getWidth() - gridSize * 7), (float) (gridSize * 3.5));
                // trigger upgrades if days = 0

                // points (includes person icon)
                if (points > 0) {
                    g2D.drawString(String.valueOf(points), (float) (mainFrame.getWidth() - gridSize * 11), (float) (gridSize * 3.5));
                    ImageUtilities.drawImage(person, (int) (mainFrame.getWidth() - gridSize * 13.25), (int) (gridSize * 1.75));
                }

                // back arrow
                g2D.setColor(map.getColours()[12]); g2D.setStroke(new BasicStroke((float) (gridSize / 2), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 7), (int) (gridSize * 3));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 4), (int) (gridSize * 2));
                g2D.drawLine((int) (gridSize * 3), (int) (gridSize * 3), (int) (gridSize * 4), (int) (gridSize * 4));
            }

            if (controlHeld) {
                // grid
                g2D.setStroke(new BasicStroke(1)); g2D.setColor(Colour.LIGHT_VIOLET_MAGENTA);
                for (int i = 0; i < 80; i++) g2D.drawLine((int) (i * gridSize), 0, (int) (i * gridSize), getHeight());
                for (int i = 0; i < 45; i++) g2D.drawLine(0, (int) (i * gridSize), getWidth(), (int) (i * gridSize));
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
                if (screenState == Screen.MAIN_MENU) {
                    // menu boxes
                    for (int i = 0; i < 3; i++) {
                        for (int j = 19 + 8 * i; j < 25 + 8 * i; j++) {
                            for (int k = 20; k < 60; k++) {
                                if (gridX == k && gridY == j) {
                                    switch (i) {
                                        case 0 -> screenState = Screen.LEVEL_SELECT;
                                        case 1 -> screenState = Screen.SETTINGS;
                                        case 2 -> System.exit(0);
                                    }
                                }
                            }
                        }
                    }
                } else if (screenState == Screen.LEVEL_SELECT) {
                    // back arrow
                    if (gridX >= 3 && gridX < 7 && gridY >= 2 && gridY < 4) screenState = Screen.MAIN_MENU;

                    // left and right arrows
                    if (gridX >= 4 && gridX < 7 && gridY >= 20 && gridY < 25) if (levelSelectIndex > 0) levelSelectIndex--;
                    if (gridX >= 73 && gridX < 76 && gridY >= 20 && gridY < 25) if (levelSelectIndex < MetroMap.cities.length - 3) levelSelectIndex++;

                    // level boxes
                    for (int i = 0; i < 3; i++) {
                        if (gridX >= 11 + 21 * i && gridX < 11 + 21 * i + 16 && gridY >= 10 && gridY < 35) {
                            mapImage = mapSetup(Map.values()[levelSelectIndex + i]);
                            screenState = Screen.GAME;
                            break;
                        }
                    }
                } else if (screenState == Screen.SETTINGS) {
                    // back arrow
                    if (gridX >= 3 && gridX < 7 && gridY >= 2 && gridY < 4) screenState = Screen.MAIN_MENU;
                } else if (screenState == Screen.GAME) {
                    // back arrow
                    if (gridX >= 3 && gridX < 7 && gridY >= 2 && gridY < 4) screenState = Screen.LEVEL_SELECT;

                    // add/remove station to/from line
                    for (Station station : stations) {
                        if (station.getGridX() == gridX && station.getGridY() == gridY) {
                            if (!lines[currentLine].getStations().contains(station)) {
                                station.setDiagonal(lines[currentLine], shiftHeld);
                                if (altHeld) lines[currentLine].getStations().addFirst(station);
                                else lines[currentLine].addStation(station);
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

            if (screenState == Screen.GAME) {
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
                case (KeyEvent.VK_ALT) -> altHeld = true;
            }

            if (screenState == Screen.LEVEL_SELECT) {
                // back arrow
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) screenState = Screen.MAIN_MENU;

                // left and right arrows
                if (e.getKeyCode() == KeyEvent.VK_LEFT && levelSelectIndex > 0) levelSelectIndex--;
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && levelSelectIndex < MetroMap.cities.length - 3) levelSelectIndex++;
            } else if (screenState == Screen.SETTINGS) {
                // back arrow
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) screenState = Screen.MAIN_MENU;
            } else if (screenState == Screen.GAME) {
                // back arrow
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) screenState = Screen.LEVEL_SELECT;

                // line selection
                if (e.getKeyCode() >= KeyEvent.VK_1 && e.getKeyCode() <= KeyEvent.VK_7) {
                    if (lines[e.getKeyCode() - KeyEvent.VK_1] != null) currentLine = e.getKeyCode() - KeyEvent.VK_1;
                }

                // pause/unpause
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (tickRate != 0) tickRate = 0;
                    else tickRate = regularTickRate;
                }

                // speed up
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (tickRate < 2) tickRate++;
                    regularTickRate = tickRate;
                }

                // slow down
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (tickRate > 0) tickRate--;
                    if (tickRate != 0) regularTickRate = tickRate;
                }

                // EDIT/DEBUG MODE!!
//                if (controlHeld) {
//                    if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {
//                        stations.add(new Station(gridX, gridY, Shape.values()[e.getKeyCode() - KeyEvent.VK_0]));
//                    }
//                }
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
                case (KeyEvent.VK_ALT) -> altHeld = false;
            }
        }

    }

}
