package fr.kahlouch.coding_game.games.mars_lander;

import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipState;
import fr.kahlouch.coding_game.games.mars_lander.model.World;
import fr.kahlouch.coding_game.games.mars_lander.model.factory.ShipFactory;
import fr.kahlouch.genetic.algorithms._genetic.GeneticAlgorithm;
import fr.kahlouch.genetic.algorithms.filling.Filling;
import fr.kahlouch.genetic.algorithms.filling.FillingAlgorithm;
import fr.kahlouch.genetic.algorithms.mating.Mating;
import fr.kahlouch.genetic.algorithms.mating.MatingAlgorithm;
import fr.kahlouch.genetic.algorithms.mutation.Mutation;
import fr.kahlouch.genetic.algorithms.mutation.MutationAlgorithm;
import fr.kahlouch.genetic.algorithms.pairing.Pairing;
import fr.kahlouch.genetic.algorithms.pairing.PairingAlgorithm;
import fr.kahlouch.genetic.algorithms.selection.Selection;
import fr.kahlouch.genetic.algorithms.selection.SelectionAlgorithm;
import fr.kahlouch.genetic.factory.GeneticFactory;
import fr.kahlouch.genetic.population.Gene;
import fr.kahlouch.genetic.population.Individual;
import fr.kahlouch.genetic.population.NewBornGeneration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public enum Resolver {
    INSTANCE;

    private final int chromosomeSize = 200;
    private final int populationSize = 100;
    private final GeneticFactory shipFactory = new ShipFactory(chromosomeSize);
    private final GeneticAlgorithm geneticAlgorithm = GeneticAlgorithm.builder(populationSize, shipFactory)
            .filling(new Filling(FillingAlgorithm.RANDOM_BREED_BEST, 10))
            .mutation(new Mutation(MutationAlgorithm.RESET, 0.01))
            .mating(new Mating(MatingAlgorithm.TWO_POINTS))
            .pairing(new Pairing(PairingAlgorithm.FITTEST, 60))
            .selection(new Selection(SelectionAlgorithm.FITTEST, 10))
            .build();

    public GeneticAlgorithm getGeneticAlgorithm() {
        return geneticAlgorithm;
    }

    public GeneticFactory getShipFactory() {
        return shipFactory;
    }

    public void loadWorldAndShipState(InputStream is) {
        final var scanner = new Scanner(is);
        World.loadWorld(scanner);
        ShipState.loadInitialState(scanner);
    }

    public void loadShipState(InputStream is) {
        final var scanner = new Scanner(is);
        ShipState.loadInitialState(scanner);
    }

    public NewBornGeneration firstGeneration() {
        final List<Individual> individuals = new ArrayList<>();
        ShipFactory.ALL_EXISTING_GENES_MAP.forEach((_, shipGene) -> {
            List<Gene> chromosome = new ArrayList<>();
            for (var i = 0; i < chromosomeSize; ++i) {
                chromosome.add(shipGene);
            }
            individuals.add(new Ship(chromosome));
        });
        for (var i = individuals.size(); i < populationSize; ++i) {
            individuals.add(shipFactory.createRandomIndividual());
        }
        return new NewBornGeneration(1, individuals);
    }
}
