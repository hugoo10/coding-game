package fr.kahlouch.coding_game.games.mars_lander.model;

import fr.kahlouch.codingame.marslander.physics.Acceleration;
import fr.kahlouch.codingame.marslander.physics.Position;
import javafx.geometry.Point2D;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class World {
    private static World WORLD = null;
    public static final Acceleration MARS_GRAVITY = new Acceleration(0, -3.711);

    private final List<Position> surface = new ArrayList<>();
    private final List<Line2D.Double> surfaceLine = new ArrayList<>();
    private Position landingZoneStart = null;
    private int landingZoneStartIdx;
    private Position landingZoneEnd = null;
    private int landingZoneEndIdx;
    private Position landingZoneMiddle = null;
    private Position lowerLeft = new Position(0, 0);
    private Position upperRight = new Position(7000, 3000);
    private double surfaceLength = 0;
    private double landingZoneLength = 0;

    private World(Scanner scanner) {
        int surfaceN = scanner.nextInt();
        for (int i = 0; i < surfaceN; i++) {
            this.addPointToSurface(scanner.nextInt(), scanner.nextInt());
        }
        for (int i = 1; i < this.surface.size(); ++i) {
            Position fromS = this.surface.get(i - 1);
            Position toS = this.surface.get(i);
            Line2D.Double surfaceLine = new Line2D.Double(fromS.getX(), fromS.getY(), toS.getX(), toS.getY());
            surfaceLength += fromS.distance(toS);
            this.surfaceLine.add(surfaceLine);
        }
        this.findLandingZone();
    }

    public static void loadWorld(Scanner scanner) {
        if (WORLD == null && scanner != null) {
            WORLD = new World(scanner);
        } else {
            throw new RuntimeException("Cannot call this method twice");
        }
    }

    public static World getInstance() {
        return WORLD;
    }

    private void findLandingZone() {
        for (int i = 0; this.landingZoneEnd == null && i < this.surface.size() - 1; ++i) {
            if (this.surface.get(i).getY() == this.surface.get(i + 1).getY() && this.surface.get(i).distance(this.surface.get(i + 1)) >= 1000) {
                this.landingZoneStart = this.surface.get(i);
                this.landingZoneStartIdx = i;
                this.landingZoneEnd = this.surface.get(i + 1);
                this.landingZoneEndIdx = i + 1;
                this.landingZoneMiddle = new Position((this.landingZoneEnd.getX() - this.landingZoneStart.getX()) / 2, this.landingZoneEnd.getY());
                this.landingZoneLength = this.landingZoneEnd.distance(this.landingZoneMiddle);
            }
        }
    }

    private void addPointToSurface(int x, int y) {
        this.surface.add(new Position(x, y));
    }

    public boolean isBetweenLandingZone(Position position) {
        if (landingZoneEnd == null || landingZoneStart == null) {
            return false;
        }
        return landingZoneStart.getX() + 50 < position.getX() && position.getX() < landingZoneEnd.getX() - 50;
    }

    public boolean isOutBound(Position from, Position to) {
        return from.getX() < lowerLeft.getX() || from.getX() > upperRight.getX() || from.getY() < lowerLeft.getY() || from.getY() > upperRight.getY();
    }

    public Point2D pathCrossSurface(Position from, Position to) {
        Line2D.Double lineToTest = new Line2D.Double(from.getX(), from.getY(), to.getX(), to.getY());
        for (Line2D.Double line : this.surfaceLine) {
            Point2D point2D = getIntersectionPoint(line, lineToTest);
            if (point2D != null) {
                return point2D;
            }
        }
        return null;
    }

    public static Point2D getIntersectionPoint(Line2D.Double line1, Line2D.Double line2) {
        Point2D vector1 = new Point2D(line1.x2 - line1.x1, line1.y2 - line1.y1);
        Point2D vector2 = new Point2D(line2.x2 - line2.x1, line2.y2 - line2.y1);

        if (vector1.getX() == 0 && vector2.getX() == 0) {
            return null;
        }

        double vector1X = vector1.getY() / vector1.getX();
        double vector1Y = line1.y1 - vector1X * line1.x1;
        double vector2X = vector2.getY() / vector2.getX();
        double vector2Y = line2.y1 - vector2X * line2.x1;

        final double intersectX;
        final double intersectY;

        if (vector1.getX() == 0) {
            intersectX = line1.x1;
            intersectY = vector2X * intersectX + vector2Y;
        } else if (vector2.getX() == 0) {
            intersectX = line2.x1;
            intersectY = vector1X * intersectX + vector1Y;
        } else {
            intersectX = (vector1Y - vector2Y) / (vector2X - vector1X);
            intersectY = vector1X * intersectX + vector1Y;
        }

        Point2D solution = new Point2D(intersectX, intersectY);
        if (
                ((solution.getX() >= line1.x1 && solution.getX() <= line1.x2) || (solution.getX() <= line1.x1 && solution.getX() >= line1.x2)) &&
                        ((solution.getY() >= line1.y1 && solution.getY() <= line1.y2) || (solution.getY() <= line1.y1 && solution.getY() >= line1.y2)) &&
                        ((solution.getX() >= line2.x1 && solution.getX() <= line2.x2) || (solution.getX() <= line2.x1 && solution.getX() >= line2.x2)) &&
                        ((solution.getY() >= line2.y1 && solution.getY() <= line2.y2) || (solution.getY() <= line2.y1 && solution.getY() >= line2.y2))
        ) {
            return solution;
        }
        return null;
    }


    public int nbCrossBeforeSurface(Position from) {
        Line2D lineToTest = new Line2D.Double(from.getX(), from.getY(), landingZoneMiddle.getX(), landingZoneMiddle.getY());
        int count = 0;
        for (Line2D line : this.surfaceLine) {
            if (line.intersectsLine(lineToTest)) {
                ++count;
            }
        }
        return count;
    }

    public Position getLandingZoneMiddle() {
        return landingZoneMiddle;
    }

    public Position getLowerLeft() {
        return lowerLeft;
    }

    public Position getUpperRight() {
        return upperRight;
    }

    public List<Position> getSurface() {
        return surface;
    }

    public double getSurfaceLength() {
        return surfaceLength;
    }

    public List<Line2D.Double> getSurfaceLine() {
        return surfaceLine;
    }

    public int getLandingZoneStartIdx() {
        return landingZoneStartIdx;
    }

    public int getLandingZoneEndIdx() {
        return landingZoneEndIdx;
    }

    public double getLandingZoneLength() {
        return landingZoneLength;
    }
}
