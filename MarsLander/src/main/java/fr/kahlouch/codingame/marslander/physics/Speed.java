package fr.kahlouch.codingame.marslander.physics;


import javafx.geometry.Point2D;

public class Speed extends Point2D {
    public Speed(double x, double y) {
        super(x, y);
    }


    public Position atTime(double time) {
        return new Position(getX() * time, getY() * time);
    }

    public boolean speedXUnder(double limitX) {
        return Math.abs(this.getX()) < Math.abs(limitX);
    }

    public boolean speedYUnder(double limitY) {
        return Math.abs(this.getY()) < Math.abs(limitY);
    }

    public boolean speedXUnder(Speed speed) {
        return speedXUnder(speed.getX());
    }

    public boolean speedYUnder(Speed speed) {
        return speedYUnder(speed.getY());
    }

    public boolean speedUnder(Speed limitSpeed) {
        return this.speedUnder(limitSpeed.getX(), limitSpeed.getY());
    }

    public boolean speedUnder(double limitX, double limitY) {
        return this.speedXUnder(limitX) && this.speedYUnder(limitY);
    }

    public Speed plus(Speed speed) {
        return this.plus(speed.getX(), speed.getY());
    }

    public Speed apply(Acceleration acceleration) {
        return this.plus(acceleration.getX(), acceleration.getY());
    }

    public Speed plus(double x, double y) {
        return new Speed(this.getX() + x, this.getY() + y);
    }
}
