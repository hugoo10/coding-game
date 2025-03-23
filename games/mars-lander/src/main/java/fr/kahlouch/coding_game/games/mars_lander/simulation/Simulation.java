package fr.kahlouch.coding_game.games.mars_lander.simulation;

import fr.kahlouch.coding_game.games.mars_lander.Resolver;
import fr.kahlouch.coding_game.games.mars_lander.path_render.Mars;
import fr.kahlouch.genetic.algorithms._genetic.GeneticAlgorithmExecutionCommand;
import fr.kahlouch.genetic.utils.HistoryType;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.Duration;

public class Simulation extends Application {
    @Override
    public void start(Stage stage) {
        try (
                final var inputStream = Simulation.class.getClassLoader().getResourceAsStream(EarthSurface.START_RIGHT_SIDE.getFilename());
        ) {
            Resolver.INSTANCE.loadWorldAndShipState(inputStream);
            final var command = GeneticAlgorithmExecutionCommand.builder()
                    .firstPopulation(Resolver.INSTANCE.firstGeneration())
                    .fitnessCap(300D)
                    .historyType(HistoryType.ONLY_BEST)
                    .build();

            final var history = Resolver.INSTANCE.getGeneticAlgorithm().compute(command);
            new Mars(history, stage).start();
        } catch (Exception ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
