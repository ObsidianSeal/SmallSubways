/*
 * TITLE: Locomotive
 * AUTHOR: Rami Hijazi
 * DATE: Tuesday, May 28th, 2024
 * DESCRIPTION: ?
 */

package objects;

/**
 *
 */
import java.awt.*;
import java.awt.event.*;

public class Locomotive implements MouseListener, MouseMotionListener {
        private int x, y;
        private int width, height;
        private Color color;
        private boolean isDragging;
        private MetroLine line;
        private int stationIndex;

        public Locomotive(int x, int y, int width, int height, Color color, MetroLine line) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            this.isDragging = false;
            this.line = line;
            this.stationIndex = 0;
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fillRect(x, y, width, height);
        }

        public void moveToNextStation() {
            if (stationIndex < line.getStations().size() - 1) {
                Station currentStation = line.getStations().get(stationIndex);
                Station nextStation = line.getStations().get(stationIndex + 1);
                x = (int) nextStation.getX();
                y = (int) nextStation.getY();
                stationIndex++;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getX() >= x && e.getX() <= x + width && e.getY() >= y && e.getY() <= y + height) {
                isDragging = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            isDragging = false;
            snapToLine();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (isDragging) {
                x = e.getX() - width / 2;
                y = e.getY() - height / 2;
            }
        }

        private void snapToLine() {
            double minDistance = Double.MAX_VALUE;
            Point closestPoint = null;

            for (int i = 0; i < line.getStations().size() - 1; i++) {
                Station station1 = line.getStations().get(i);
                Station station2 = line.getStations().get(i + 1);
                Point p1 = new Point((int) station1.getX(), (int) station1.getY());
                Point p2 = new Point((int) station2.getX(), (int) station2.getY());
                Point closest = getClosestPointOnLine(p1, p2, new Point(x + width / 2, y + height / 2));

                double distance = closest.distance(x + width / 2, y + height / 2);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = closest;
                }
            }

            if (closestPoint != null) {
                x = closestPoint.x - width / 2;
                y = closestPoint.y - height / 2;
            }
        }

        private Point getClosestPointOnLine(Point p1, Point p2, Point p) {
            double dx = p2.x - p1.x;
            double dy = p2.y - p1.y;
            double u = ((p.x - p1.x) * dx + (p.y - p1.y) * dy) / (dx * dx + dy * dy);
            u = Math.max(0, Math.min(1, u));
            return new Point((int) (p1.x + u * dx), (int) (p1.y + u * dy));
        }

        // Unused mouse event methods
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {}
    }

