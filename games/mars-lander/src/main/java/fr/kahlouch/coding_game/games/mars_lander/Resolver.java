package fr.kahlouch.coding_game.games.mars_lander;

import fr.kahlouch.coding_game.games.mars_lander.model.*;
import fr.kahlouch.genetic.algorithm.execution.Algorithm;
import fr.kahlouch.genetic.algorithm.execution.ExecutionLimit;
import fr.kahlouch.genetic.algorithm.execution.context.ExecutionContext;
import fr.kahlouch.genetic.algorithm.execution.context.step.crossover.CrossoverType;
import fr.kahlouch.genetic.algorithm.execution.context.step.elitism.ElitismType;
import fr.kahlouch.genetic.algorithm.execution.context.step.filling.FillingType;
import fr.kahlouch.genetic.algorithm.execution.context.step.mutation.MutationType;
import fr.kahlouch.genetic.algorithm.execution.context.step.repair.Repair;
import fr.kahlouch.genetic.algorithm.execution.context.step.selection.SelectionType;
import fr.kahlouch.genetic.algorithm.execution.listener.ExecutionListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public enum Resolver {
    INSTANCE;

    private final int minPopulationSize = 100;
    private final int maxPopulationSize = 150;
    private final int individualSize = 200;
    private final ShipExecutionHelper shipExecutionHelper = new ShipExecutionHelper();

    private final ExecutionContext<ShipGene, Ship, ShipPath> executionContext = ExecutionContext.<ShipGene, Ship, ShipPath>builder()
            .helper(shipExecutionHelper)
            .individualSize(individualSize)
            .populationSize(minPopulationSize, maxPopulationSize)
            .elitism(ElitismType.FITTEST, 0.1)
            .selection(SelectionType.FITTEST, 0.3)
            .crossover(CrossoverType.CONTINUOUS)
            .mutation(MutationType.GAUSS, 0.01)
            .filling(FillingType.RANDOM_BREED_BEST, 0.1)
            .repair(Repair.noop());

    public Ship executeAlgorithm(List<Ship> firstGeneration, ExecutionLimit limit, List<ExecutionListener<ShipGene, Ship, ShipPath>> listeners) {
        return new Algorithm<>(firstGeneration)
                .findBest(this.executionContext, limit, listeners);
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

    public List<Ship> firstGeneration() {
        final var individuals = new ArrayList<Ship>();

        for (var angle = -90; angle <= 90; angle += 15) {
            for (var power = 0; power <= 4; ++power) {
                final var shipGene = ShipGene.of(angle, power);
                final var genes = Stream.generate(() -> shipGene).limit(individualSize).toList();
                individuals.add(new Ship(genes));
            }
        }

        for (var population = individuals.size(); population < minPopulationSize; ++population) {
            final var genes = Stream.generate(shipExecutionHelper::createRandomGene)
                    .limit(individualSize).toList();
            individuals.add(new Ship(genes));
        }

        return individuals;
    }
}
