package fr.kahlouch.coding_game.games.mars_lander.model;

import fr.kahlouch.codingame.marslander.model.factory.ShipGeneFactory;
import fr.kahlouch.codingame.marslander.physics.Acceleration;
import fr.kahlouch.codingame.marslander.physics.Position;
import fr.kahlouch.codingame.marslander.physics.Speed;

import java.util.Scanner;

public class ShipState {
    private static ShipState INITIAL_SHIP_STATE = null;

    private final Position position;
    private final Speed speed;
    private final int fuel;
    private final int angle;
    private final int power;
    private final int time;

    public static void loadInitialState(Scanner scanner) {
        if (scanner != null) {
            INITIAL_SHIP_STATE = new ShipState(scanner);
        }
    }

    public static ShipState getInitialShipState() {
        return INITIAL_SHIP_STATE;
    }

    public static void setInitialShipState(ShipState initialShipState) {
        INITIAL_SHIP_STATE = initialShipState;
    }

    private ShipState(Scanner scanner) {
        this.position = new Position(scanner.nextInt(), scanner.nextInt());
        this.speed = new Speed(scanner.nextInt(), scanner.nextInt());
        this.fuel = scanner.nextInt();
        this.angle = scanner.nextInt();
        this.power = scanner.nextInt();
        this.time = 0;
    }

    public ShipState(ShipState previousState, ShipGene gene) {
        this.time = previousState.time + 1;
        if (gene.getAngle() > previousState.angle) {
            this.angle = previousState.angle + 15;
        } else if (gene.getAngle() < previousState.angle) {
            this.angle = previousState.angle - 15;
        } else {
            this.angle = previousState.angle;
        }
        if (gene.getPower() > previousState.power) {
            this.power = previousState.power + 1;
        } else if (gene.getPower() < previousState.power) {
            this.power = previousState.power - 1;
        } else {
            this.power = previousState.power;
        }
        final Acceleration acceleration = ShipGeneFactory.getGeneByAngleAndPower(this.angle, this.power).getAcceleration().plus(World.MARS_GRAVITY);
        this.speed = previousState.speed.apply(acceleration);
        this.position = previousState.position.apply(speed);
        this.fuel = previousState.fuel - this.power;
    }

    public Position getPosition() {
        return position;
    }

    public Speed getSpeed() {
        return speed;
    }

    public int getFuel() {
        return fuel;
    }

    public int getAngle() {
        return angle;
    }

    public int getPower() {
        return power;
    }

    public int getTime() {
        return time;
    }
}
