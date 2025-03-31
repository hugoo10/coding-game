package fr.kahlouch.coding_game.games.mars_lander;


import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipGene;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipPath;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipState;
import fr.kahlouch.coding_game.games.mars_lander.simulation.Simulation;
import fr.kahlouch.genetic.algorithm.execution.ExecutionLimit;
import fr.kahlouch.genetic.algorithm.execution.listener.BestIndividualWriter;
import fr.kahlouch.genetic.algorithm.execution.listener.BestPopulationListener;

import java.io.InputStream;
import java.time.Duration;
import java.util.List;

public class Player {
    private static final String MAP_FILE = "start_speed_wrong_side.txt";

    public static void main(String... args) {
        final InputStream inputStream = Simulation.class.getClassLoader().getResourceAsStream(MAP_FILE);

        Resolver.INSTANCE.loadWorldAndShipState(inputStream);

        final var listener = new BestPopulationListener<ShipGene, Ship, ShipPath>();
        final var listenerwrite = new BestIndividualWriter<ShipGene, Ship, ShipPath>();

        final var limit = new ExecutionLimit(300.0, Duration.ofMillis(90));
        var firstGeneration = Resolver.INSTANCE.firstGeneration();

        do {
            final var best = Resolver.INSTANCE.executeAlgorithm(firstGeneration, limit, List.of(listener, listenerwrite));

            if (best.getFitnessComputeResult().fitness() >= 300) {
                best.getShipStates().stream().skip(1).map(ShipState::toCommand).forEach(System.out::println);
                break;
            }

            Resolver.INSTANCE.loadShipState(inputStream);
            firstGeneration = listener.getBestPopulation().getIndividuals();
        } while (true);
    }
}
