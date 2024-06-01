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
    public static boolean[][] grid = new boolean[45][80];

    // variables
    static int mouseX;
    static int mouseY;
    static boolean shiftHeld = false;
    static boolean controlHeld = false;

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
        BufferedImage background = MetroMap.OTTAWA;

        /**
         * GraphicsPanel constructor.
         */
        GraphicsPanel() {
            this.setBackground(Color.BLACK);

            lines.add(new MetroLine(new ArrayList<Station>(), MetroMap.OTTAWA_COLOURS[(int) (Math.random() * 7)]));
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

                // spawn stations, 200 station limit
                if (stations.size() < 200) {
//                if (ticks % 150 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());
//                if (ticks % 15 == 0 && (int) (Math.random() * 15) == 0) stations.add(new Station());
                    if (ticks % 15 == 0) {
                        stations.add(new Station());
                        System.out.println(stations.size());
                    }
                }

                // EDIT/DEBUG MODE!!
                if (controlHeld) {
                    // grid
                    g2D.setColor(Colour.LIGHT_RED);
                    for (int i = 0; i < 80; i++) g2D.drawLine((int) (i * stations.getFirst().getSize()), 0, (int) (i * stations.getFirst().getSize()), getHeight());
                    for (int i = 0; i < 45; i++) g2D.drawLine(0, (int) (i * stations.getFirst().getSize()), getWidth(), (int) (i * stations.getFirst().getSize()));

                    // disallowed squares
                    for (int i = 0; i < 45; i++) {
                        for (int j = 0; j < 80; j++) {
                            if (grid[i][j]) {
                                g2D.fillRect(j * (mainFrame.getWidth() / 80), i * (mainFrame.getHeight() / 45), mainFrame.getWidth() / 80, mainFrame.getHeight() / 45);
                            }
                        }
                    }
                }

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
            // left click
            if (e.getButton() == 1) {
                // add/remove station to/from line
                for (Station station : stations) {
                    if (Math.abs(station.getX() - e.getX()) < (mainFrame.getWidth() / 64f) && Math.abs(station.getY() - e.getY()) < (mainFrame.getWidth() / 64f)) {
                        if (!lines.getFirst().getStations().contains(station)) {
                            station.setDiagonal(shiftHeld);
                            lines.getFirst().addStation(station);
                        } else {
                            lines.getFirst().removeStation(station);
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
            }

            // EDIT/DEBUG MODE!!
            if (controlHeld) {
                if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {
                    stations.add(new Station(mouseX / (mainFrame.getWidth() / 80), mouseY / (mainFrame.getHeight() / 45), Shape.values()[e.getKeyCode() - KeyEvent.VK_0]));
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
