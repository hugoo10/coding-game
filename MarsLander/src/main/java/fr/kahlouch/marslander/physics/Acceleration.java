package main.java.fr.kahlouch.marslander.physics;


import javafx.geometry.Point2D;

public class Acceleration extends Point2D {
    public Acceleration(double x, double y) {
        super(x, y);
    }

    public Speed atTime(double time) {
        return new Speed(getX() * time, getY() * time);
    }

    public Acceleration times(int constant) {
        return new Acceleration(this.getX() * constant, this.getY() * constant);
    }

    public Acceleration plus(Acceleration acceleration) {
        return new Acceleration(this.getX() + acceleration.getX(), this.getY() + acceleration.getY());
    }


}
