package fr.kahlouch.coding_game.games.mars_lander;


import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipGene;

import fr.kahlouch.coding_game.games.mars_lander.model.ShipState;
import fr.kahlouch.coding_game.games.mars_lander.simulation.Simulation;
import fr.kahlouch.genetic.algorithms._genetic.GeneticAlgorithm;
import fr.kahlouch.genetic.algorithms._genetic.GeneticAlgorithmExecutionCommand;
import fr.kahlouch.genetic.population.Individual;
import fr.kahlouch.genetic.population.NewBornGeneration;
import fr.kahlouch.genetic.utils.HistoryType;

import java.io.InputStream;
import java.time.Duration;

public class Player {
    private static final String MAP_FILE = "start_speed_wrong_side.txt";

    public static void main(String... args) {
        final InputStream inputStream = Simulation.class.getClassLoader().getResourceAsStream(MAP_FILE);

        Resolver.INSTANCE.loadWorldAndShipState(inputStream);

        var command = GeneticAlgorithmExecutionCommand.builder()
                .firstPopulation(Resolver.INSTANCE.firstGeneration())
                .fitnessCap(300D)
                .historyType(HistoryType.NO_HISTORY)
                .timeCap(Duration.ofMillis(90))
                .build();

        do {
            final var history = Resolver.INSTANCE.getGeneticAlgorithm().compute(command);
            final var best = history.getCurrentBest();

            if (best.fitness() >= 300) {
                ((Ship)best.individual()).getShipStates().stream()
                        .skip(1)
                        .map(ShipState::toCommand)
                        .forEach(System.out::println);
                break;
            }

            Resolver.INSTANCE.loadShipState(inputStream);
            final var bestGeneration = history.getCurrentBestGeneration();
            final var bestGenerationIndividuals = bestGeneration.getIndividuals().stream().map(Individual.class::cast).toList();
            final var startingGeneration = new NewBornGeneration(bestGeneration.getGenerationNumber(), bestGenerationIndividuals);
            command = command.toBuilder()
                    .firstPopulation(startingGeneration)
                    .build();

        } while (true);
    }
}
