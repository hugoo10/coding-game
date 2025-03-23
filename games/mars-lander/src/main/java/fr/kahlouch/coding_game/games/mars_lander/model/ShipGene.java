package fr.kahlouch.coding_game.games.mars_lander.model;

import fr.kahlouch.coding_game.games.mars_lander.model.factory.ShipFactory;
import fr.kahlouch.coding_game.games.mars_lander.physics.Acceleration;
import fr.kahlouch.genetic.population.BreededGene;
import fr.kahlouch.genetic.population.Gene;

public class ShipGene implements Gene {
    private int angle;
    private int power;
    private Acceleration acceleration;

    public ShipGene(int angle, int power) {
        this.angle = angle;
        this.power = power;
        this.acceleration = new Acceleration(Math.cos(Math.toRadians(this.angle + 90D)), Math.sin(Math.toRadians(this.angle + 90D))).times(this.power);
    }

    public ShipGene(int angle, int power, Acceleration acceleration) {
        this.angle = angle;
        this.power = power;
        this.acceleration = acceleration;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    @Override
    public String toString() {
        return "new ShipGene(" + angle +
                ", " + power + ", new Acceleration(" + acceleration.getX() + ", " + acceleration.getY() +
                "));";
    }

    @Override
    public BreededGene breed(Gene gene, double random) {
        final ShipGene mate = (ShipGene) gene;
        double angle1 = (random * this.angle) + (1 - random) * mate.angle;
        double angle2 = (random * mate.angle) + (1 - random) * this.angle;
        double power1 = (random * this.power) + (1 - random) * mate.power;
        double power2 = (random * mate.power) + (1 - random) * this.power;

        final var gene1 = ShipFactory.getGeneByAngleAndPower(ShipFactory.convertAngleDoubleToInt(angle1), ShipFactory.convertPowerDoubleToInt(power1));
        final var gene2 = ShipFactory.getGeneByAngleAndPower(ShipFactory.convertAngleDoubleToInt(angle2), ShipFactory.convertPowerDoubleToInt(power2));
        return new BreededGene(gene1, gene2);
    }
}
