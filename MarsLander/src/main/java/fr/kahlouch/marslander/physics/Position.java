package fr.kahlouch.marslander.physics;


import javafx.geometry.Point2D;

public class Position extends Point2D {
    public Position(double x, double y) {
        super(x, y);
    }

    public Position apply(Speed speed) {
        return this.plus(speed.getX(), speed.getY());
    }

    public Position plus(Position position) {
        return this.plus(position.getX(), position.getY());
    }

    public Position plus(double x, double y) {
        return new Position(this.getX() + x, this.getY() + y);
    }
}
