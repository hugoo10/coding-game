package fr.kahlouch.coding_game.games.mars_lander.model;

import fr.kahlouch.coding_game.games.mars_lander.physics.Acceleration;
import fr.kahlouch.genetic.algorithm.vo.Gene;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class ShipGene implements Gene {
    private static final Map<Integer, Map<Integer, ShipGene>> MAP_ANGLE_POWER = new ConcurrentHashMap<>();


    private final int angle;
    private final int power;
    private final Acceleration acceleration;

    private ShipGene(int angle, int power) {
        this.angle = angle;
        this.power = power;
        this.acceleration = new Acceleration(StrictMath.cos(Math.toRadians(this.angle + 90D)), StrictMath.sin(Math.toRadians(this.angle + 90D)))
                .times(this.power);
    }

    public static ShipGene of(double angle, double power) {
        final var effectiveAngle = convertAngleDoubleToInt(angle);
        final var effectivePower = convertPowerDoubleToInt(power);

        return MAP_ANGLE_POWER.computeIfAbsent(effectiveAngle, _ -> new ConcurrentHashMap<>())
                .computeIfAbsent(effectivePower, _ -> new ShipGene(effectiveAngle, effectivePower));
    }

    public int getAngle() {
        return angle;
    }

    public int getPower() {
        return power;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }


    public static int convertAngleDoubleToInt(double angle) {
        int angleInt = ((Long) Math.round(angle)).intValue();
        if (angleInt <= -90) return -90;
        if (angleInt >= 90) return 90;

        return Stream.iterate(-90, i -> i <= 90, i -> i + 15)
                .gather(Gatherers.windowSliding(2))
                .map(list -> roundIfBetween(angleInt, list.getFirst(), list.getLast()))
                .mapMulti(Optional<Integer>::ifPresent)
                .findFirst()
                .orElseThrow();
    }

    private static Optional<Integer> roundIfBetween(int number, int lower, int upper) {
        if (number < lower || number > upper) return Optional.empty();
        final var lowerDiff = number - lower;
        final var upperDiff = upper - number;

        if (lowerDiff < upperDiff) return Optional.of(lower);
        return Optional.of(upper);
    }


    public static int convertPowerDoubleToInt(double power) {
        int powerInt = ((Long) Math.round(power)).intValue();
        if (powerInt <= 0) return 0;
        return Math.min(powerInt, 4);
    }
}
