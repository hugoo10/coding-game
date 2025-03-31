package fr.kahlouch.coding_game.games.mars_lander.simulation;

import fr.kahlouch.coding_game.games.mars_lander.Resolver;
import fr.kahlouch.coding_game.games.mars_lander.model.Ship;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipGene;
import fr.kahlouch.coding_game.games.mars_lander.model.ShipPath;
import fr.kahlouch.coding_game.games.mars_lander.path_render.Mars;
import fr.kahlouch.genetic.algorithm.execution.ExecutionLimit;
import fr.kahlouch.genetic.algorithm.execution.listener.BestIndividualWriter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

public class Simulation extends Application {
    @Override
    public void start(Stage stage) {
        try (
                final var inputStream = Simulation.class.getClassLoader().getResourceAsStream(EarthSurface.DEEP.getFilename());
        ) {
            final var bestIndividualWriter = new BestIndividualWriter<ShipGene, Ship, ShipPath>();

            Resolver.INSTANCE.loadWorldAndShipState(inputStream);
            final var firstGeneration = Resolver.INSTANCE.firstGeneration();
            final var limit = new ExecutionLimit(300.0, null);


            final var mars = new Mars(stage);
            Thread.ofPlatform().start(() -> {
                Resolver.INSTANCE.executeAlgorithm(firstGeneration, limit, List.of(bestIndividualWriter, mars));
            });

            mars.start();
        } catch (Exception ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
