package fr.kahlouch.coding_game.games.mars_lander;

import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipGene;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipPath;
import fr.kahlouch.genetic.algorithm.helper.ExecutionHelper;

import java.util.List;
import java.util.Random;

public class ShipExecutionHelper extends ExecutionHelper<ShipGene, Ship, ShipPath> {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public Ship createIndividual(List<ShipGene> genes) {
        return new Ship(genes);
    }

    @Override
    public ShipGene breedGenes(ShipGene gene1, ShipGene gene2, double random) {
        double angle = (random * gene1.getAngle()) + (1 - random) * gene2.getAngle();
        double power = (random * gene1.getPower()) + (1 - random) * gene2.getPower();

        return ShipGene.of(angle, power);
    }

    @Override
    public ShipGene createRandomGene() {
        return ShipGene.of(random.nextInt(181) - 90, random.nextInt(5));
    }

    @Override
    protected ShipGene createFromGaussian(ShipGene shipGene, double gaussian) {
        final var angle = gaussian * 90 + shipGene.getAngle();
        final var power = gaussian * 2 + shipGene.getPower();

        return ShipGene.of(angle, power);
    }
}
