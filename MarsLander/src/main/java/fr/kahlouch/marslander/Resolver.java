package main.java.fr.kahlouch.marslander;

import fr.kahlouch.genetic.GeneticAlgorithm;
import fr.kahlouch.genetic.GeneticAlgorithmParams;
import fr.kahlouch.genetic.algorithms.filling.FillingType;
import fr.kahlouch.genetic.algorithms.mating.MatingType;
import fr.kahlouch.genetic.algorithms.mutation.MutationType;
import fr.kahlouch.genetic.algorithms.pairing.PairingType;
import fr.kahlouch.genetic.algorithms.selection.SelectionType;
import fr.kahlouch.genetic.population.Gene;
import fr.kahlouch.genetic.population.Individual;
import fr.kahlouch.genetic.population.Population;
import fr.kahlouch.marslander.model.Ship;
import fr.kahlouch.marslander.model.ShipState;
import fr.kahlouch.marslander.model.World;
import fr.kahlouch.marslander.model.factory.ShipFactory;
import fr.kahlouch.marslander.model.factory.ShipGeneFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Resolver {
    private static final Resolver resolver = new Resolver();
    private final int chromosomeSize = 200;
    private final int populationSize = 100;
    private final ShipGeneFactory shipGeneFactory = new ShipGeneFactory();
    private final ShipFactory shipFactory = new ShipFactory(chromosomeSize, shipGeneFactory);
    private final GeneticAlgorithmParams params = GeneticAlgorithmParams.builder()
            .filling(FillingType.RANDOM_BREED_BEST)
            .mutation(MutationType.RESET)
            .mating(MatingType.TWO_POINTS)
            .pairing(PairingType.FITTEST)
            .selection(SelectionType.FITTEST)
            .geneFactory(this.shipGeneFactory)
            .individualFactory(this.shipFactory)
            .populationSize(populationSize)
            .selectionSize(10)
            .pairingSize(60)
            .mutationOdd(0.01)
            .fillingRetrieveTopSize(10)
            .build();

    public static Resolver getInstance() {
        return resolver;
    }

    public GeneticAlgorithm loadData(InputStream is, Double fitnessCap, Long timeCapInMillis) {
        final var scanner = new Scanner(is);
        World.loadWorld(scanner);
        ShipState.loadInitialState(scanner);
        return new GeneticAlgorithm(params,  fitnessCap, timeCapInMillis);
    }

    public GeneticAlgorithm reloadData(InputStream is, Double fitnessCap, Long timeCapInMillis) {
        final var scanner = new Scanner(is);
        ShipState.loadInitialState(scanner);
        return new GeneticAlgorithm(params, fitnessCap, timeCapInMillis);
    }

    public Population firstGeneration() {
        final List<Individual> individuals = new ArrayList<>();
        ShipGeneFactory.ALL_EXISTING_GENES_MAP.forEach((key, shipGene) -> {
            List<Gene> chromosome = new ArrayList<>();
            for (var i = 0; i < chromosomeSize; ++i) {
                chromosome.add(shipGene);
            }
            individuals.add(new Ship(chromosome));
        });
        for (var i = individuals.size(); i < populationSize; ++i) {
            individuals.add(shipFactory.createRandom());
        }
        return new Population(individuals);
    }
}
