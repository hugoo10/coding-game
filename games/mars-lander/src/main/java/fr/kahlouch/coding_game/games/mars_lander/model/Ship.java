package fr.kahlouch.coding_game.games.mars_lander.model;


import fr.kahlouch.coding_game.games.mars_lander.physics.Speed;
import fr.kahlouch.genetic.population.EvaluatedIndividual;
import fr.kahlouch.genetic.population.Gene;
import fr.kahlouch.genetic.population.NewBornIndividual;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Ship extends NewBornIndividual {
    private static final Speed MAX_LANDING_SPEED = new Speed(20, 40);
    private final List<ShipState> shipStates = new ArrayList<>();
    private Point2D intersectSurfacePoint;

    public Ship(List<Gene> chromosome) {
        super(chromosome);
    }

    @Override
    public EvaluatedIndividual computeFitness() {
        computePath();
        final var fitness = computeMark();
        return new EvaluatedIndividual(this, fitness);
    }

    public List<ShipState> getShipStates() {
        return shipStates;
    }

    private void computePath() {
        ShipState from = ShipState.getInitialShipState();
        ShipState to;
        this.shipStates.add(from);
        final Iterator<Gene> geneIt = this.chromosome.iterator();
        boolean isOutBound;
        do {
            to = new ShipState(from, (ShipGene) geneIt.next());
            isOutBound = World.getInstance().isOutBound(from.getPosition(), to.getPosition());
            this.intersectSurfacePoint = World.getInstance().pathCrossSurface(from.getPosition(), to.getPosition());
            from = to;
            this.shipStates.add(to);
        } while (geneIt.hasNext() && (!isOutBound && this.intersectSurfacePoint == null));
    }

    private double computeMark() {
        final ShipState beforeLastShipState = this.shipStates.get(this.shipStates.size() - 2);
        final ShipState lastShipState = this.shipStates.get(this.shipStates.size() - 1);
        double fitness;

        final World world = World.getInstance();
        if (world.isBetweenLandingZone(lastShipState.getPosition()) && world.isBetweenLandingZone(beforeLastShipState.getPosition()) && lastShipState.getPosition().getY() <= world.getLandingZoneMiddle().getY() && beforeLastShipState.getPosition().getY() >= world.getLandingZoneMiddle().getY()) {
            fitness = 100;
            if (beforeLastShipState.getSpeed().speedYUnder(MAX_LANDING_SPEED) && lastShipState.getSpeed().speedYUnder(MAX_LANDING_SPEED)) {
                fitness = 150;
                if (beforeLastShipState.getSpeed().speedXUnder(MAX_LANDING_SPEED) && lastShipState.getSpeed().speedXUnder(MAX_LANDING_SPEED)) {
                    fitness = 200;
                    if (beforeLastShipState.getAngle() == 0 && lastShipState.getAngle() == 0) {
                        fitness = 300;
                    } else {
                        fitness += (1 - Math.abs(lastShipState.getAngle()) / 90D) * 100;
                    }
                } else {
                    fitness += ((MAX_LANDING_SPEED.getX() / Math.abs(lastShipState.getSpeed().getX())) * 50);
                }
            } else {
                fitness += (MAX_LANDING_SPEED.getY() / Math.abs(lastShipState.getSpeed().getY())) * 50;
            }
        } else {
            if (this.intersectSurfacePoint != null) {
                int idx = -1;
                Boolean right = null;
                for (int i = 0; i < world.getSurface().size() - 1; ++i) {
                    Point2D from = world.getSurface().get(i);
                    Point2D to = world.getSurface().get(i + 1);
                    if (
                            ((this.intersectSurfacePoint.getX() >= from.getX() && this.intersectSurfacePoint.getX() <= to.getX()) ||
                                    (this.intersectSurfacePoint.getX() <= from.getX() && this.intersectSurfacePoint.getX() >= to.getX())) &&
                                    ((this.intersectSurfacePoint.getY() >= from.getY() && this.intersectSurfacePoint.getY() <= to.getY()) ||
                                            (this.intersectSurfacePoint.getY() <= from.getY() && this.intersectSurfacePoint.getY() >= to.getY()))
                    ) {
                        right = i >= world.getLandingZoneEndIdx();
                        idx = right ? i : i + 1;
                        break;
                    }
                }
                double distance = world.getLandingZoneLength() + this.intersectSurfacePoint.distance(world.getSurface().get(idx));
                int from = right ? world.getLandingZoneEndIdx() : idx;
                int to = right ? idx : world.getLandingZoneStartIdx();
                for (int i = from; i < to; ++i) {
                    distance += world.getSurface().get(i).distance(world.getSurface().get(i + 1));
                }
                fitness = (1 - distance / world.getSurfaceLength()) * 100;

            } else {
                fitness = -(world.getLandingZoneMiddle().distance(lastShipState.getPosition()) / world.getUpperRight().getX()) * 100;
            }
        }
        return fitness;
    }

    @Override
    public String toString() {
        if (this.shipStates.isEmpty()) {
            return "Ship ready to launch!";
        } else {
            final ShipState last = this.shipStates.getLast();
            return String.format("POSITION: %s; SPEED: %s; ANGLE: %s", last.getPosition(), last.getSpeed(), last.getAngle());
        }
    }

    public void printShipStates() {
        System.out.println(getSolution());
    }

    public String getSolution() {
        return shipStates.stream()
                .skip(1)
                .map(shipState -> String.format("%s %s", shipState.getAngle(), shipState.getPower()))
                .collect(Collectors.joining(";")) + ";0 4";
    }
}
