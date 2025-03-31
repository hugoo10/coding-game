package fr.kahlouch.coding_game.games.mars_lander.model;


import fr.kahlouch.coding_game.games.mars_lander.physics.Speed;
import fr.kahlouch.genetic.algorithm.vo.FitnessComputeResult;
import fr.kahlouch.genetic.algorithm.vo.Individual;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class Ship extends Individual<ShipGene, ShipPath> {
    private static final Speed MAX_LANDING_SPEED = new Speed(20, 40);

    public Ship(List<ShipGene> genes) {
        super(genes);
    }

    @Override
    protected FitnessComputeResult<ShipPath> computeFitness() {
        final var shipPath = computePath();
        final var fitness = computeMark(shipPath);
        return new FitnessComputeResult<>(fitness, shipPath);
    }

    public List<ShipState> getShipStates() {
        return Optional.ofNullable(this.fitnessComputeResult)
                .map(FitnessComputeResult::computeData)
                .map(ShipPath::shipStates)
                .orElseThrow();
    }

    private ShipPath computePath() {
        ShipState from = ShipState.getInitialShipState();
        ShipState to;

        final var shipStates = new ArrayList<ShipState>();
        shipStates.add(from);
        final Iterator<ShipGene> geneIt = this.genes.iterator();
        boolean isOutBound;
        Point2D intersectSurfacePoint;
        do {
            to = new ShipState(from, geneIt.next());

            isOutBound = World.getInstance().isOutBound(from.getPosition(), to.getPosition());
            intersectSurfacePoint = World.getInstance().pathCrossSurface(from.getPosition(), to.getPosition());
            from = to;
            shipStates.add(to);
        } while (geneIt.hasNext() && (!isOutBound && intersectSurfacePoint == null));
        return new ShipPath(shipStates, intersectSurfacePoint);
    }

    private double computeMark(ShipPath shipPath) {
        final ShipState beforeLastShipState = shipPath.shipStates().get(shipPath.shipStates().size() - 2);
        final ShipState lastShipState = shipPath.shipStates().getLast();

        final World worldInstance = World.getInstance();

        if (isInLandingZone(worldInstance).negate().test(beforeLastShipState, lastShipState)) {

            if (shipPath.intersectSurfacePoint() == null) {
                final var distanceFromMiddleLandingZone = worldInstance.getLandingZoneMiddle().distance(lastShipState.getPosition());
                final var worldWidth = worldInstance.getUpperRight().getX();

                return -100 * distanceFromMiddleLandingZone / worldWidth;
            }

            int idx = -1;
            Boolean right = null;
            for (int i = 0; i < worldInstance.getSurface().size() - 1; ++i) {
                Point2D from = worldInstance.getSurface().get(i);
                Point2D to = worldInstance.getSurface().get(i + 1);
                if (
                        ((shipPath.intersectSurfacePoint().getX() >= from.getX() && shipPath.intersectSurfacePoint().getX() <= to.getX()) ||
                                (shipPath.intersectSurfacePoint().getX() <= from.getX() && shipPath.intersectSurfacePoint().getX() >= to.getX())) &&
                                ((shipPath.intersectSurfacePoint().getY() >= from.getY() && shipPath.intersectSurfacePoint().getY() <= to.getY()) ||
                                        (shipPath.intersectSurfacePoint().getY() <= from.getY() && shipPath.intersectSurfacePoint().getY() >= to.getY()))
                ) {
                    right = i >= worldInstance.getLandingZoneEndIdx();
                    idx = right ? i : i + 1;
                    break;
                }
            }
            double distance = worldInstance.getLandingZoneLength() + shipPath.intersectSurfacePoint().distance(worldInstance.getSurface().get(idx));
            int from = right ? worldInstance.getLandingZoneEndIdx() : idx;
            int to = right ? idx : worldInstance.getLandingZoneStartIdx();
            for (int i = from; i < to; ++i) {
                distance += worldInstance.getSurface().get(i).distance(worldInstance.getSurface().get(i + 1));
            }
            return (1 - distance / worldInstance.getSurfaceLength()) * 100;
        }


        if (isLandingAtYSpeedUnderMaxSpeed().negate().test(beforeLastShipState, lastShipState)) {
            final var absYSpeed = Math.abs(lastShipState.getSpeed().getY());
            final var ySpeedScore = 50 * MAX_LANDING_SPEED.getY() / absYSpeed;

            return 100 + ySpeedScore;
        }

        if (isLandingAtXSpeedUnderMaxSpeed().negate().test(beforeLastShipState, lastShipState)) {
            final var absXSpeed = Math.abs(lastShipState.getSpeed().getX());
            final var xSpeedScore = 50 * MAX_LANDING_SPEED.getX() / absXSpeed;

            return 150 + xSpeedScore;
        }

        if (isLandingAtAngle0().negate().test(beforeLastShipState, lastShipState)) {
            final var absAngle = Math.abs(lastShipState.getAngle());
            final var angleScore = (1 - absAngle / 90D) * 100;
            return 200 + angleScore;
        }

        return 300;

    }

    private static BiPredicate<ShipState, ShipState> isInLandingZone(World world) {
        final BiPredicate<ShipState, ShipState> isPositionBeforeLandingInLandingZone = (beforeLandState, _) -> world.isBetweenLandingZone(beforeLandState.getPosition());
        final BiPredicate<ShipState, ShipState> isPositionAfterLandingInLandingZone = (_, afterLandState) -> world.isBetweenLandingZone(afterLandState.getPosition());
        final BiPredicate<ShipState, ShipState> isPositionBeforeLandingOverLandingZone = (beforeLandState, _) -> beforeLandState.getPosition().getY() >= world.getLandingZoneMiddle().getY();
        final BiPredicate<ShipState, ShipState> isPositionAfterLandingUnderLandingZone = (_, afterLandState) -> afterLandState.getPosition().getY() <= world.getLandingZoneMiddle().getY();

        return isPositionBeforeLandingInLandingZone.and(isPositionAfterLandingInLandingZone)
                .and(isPositionBeforeLandingOverLandingZone).and(isPositionAfterLandingUnderLandingZone);
    }

    private static BiPredicate<ShipState, ShipState> isLandingAtYSpeedUnderMaxSpeed() {
        final BiPredicate<ShipState, ShipState> isStateBeforeLandingYSpeedUnder = (beforeLandState, _) -> beforeLandState.getSpeed().speedYUnder(MAX_LANDING_SPEED);
        final BiPredicate<ShipState, ShipState> isStateAfterLandingYSpeedUnder = (_, afterLandState) -> afterLandState.getSpeed().speedYUnder(MAX_LANDING_SPEED);
        return isStateBeforeLandingYSpeedUnder.and(isStateAfterLandingYSpeedUnder);
    }

    private static BiPredicate<ShipState, ShipState> isLandingAtXSpeedUnderMaxSpeed() {
        final BiPredicate<ShipState, ShipState> isStateBeforeLandingXSpeedUnder = (beforeLandState, _) -> beforeLandState.getSpeed().speedXUnder(MAX_LANDING_SPEED);
        final BiPredicate<ShipState, ShipState> isStateAfterLandingXSpeedUnder = (_, afterLandState) -> afterLandState.getSpeed().speedXUnder(MAX_LANDING_SPEED);
        return isStateBeforeLandingXSpeedUnder.and(isStateAfterLandingXSpeedUnder);
    }

    private static BiPredicate<ShipState, ShipState> isLandingAtAngle0() {
        final BiPredicate<ShipState, ShipState> isStateBeforeLandingAtAngle0 = (beforeLandState, _) -> beforeLandState.getAngle() == 0;
        final BiPredicate<ShipState, ShipState> isStateAfterLandingAtAngle0 = (_, afterLandState) -> afterLandState.getAngle() == 0;
        return isStateBeforeLandingAtAngle0.and(isStateAfterLandingAtAngle0);
    }


    @Override

    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", fitnessComputeResult=" + fitnessComputeResult.fitness() +
                '}';
    }
}
