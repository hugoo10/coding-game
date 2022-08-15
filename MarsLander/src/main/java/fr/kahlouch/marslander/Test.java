package fr.kahlouch.marslander;

import fr.kahlouch.genetic.utils.HistoryType;
import fr.kahlouch.marslander.path_render.Mars;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Map;

public class Test extends Application {
    private static final Map<Integer, String> MAP_FILES = Map.ofEntries(
            Map.entry(0, "flat.txt"),
            Map.entry(1, "easy_right.txt"),
            Map.entry(2, "start_speed_right_side.txt"),
            Map.entry(3, "start_speed_wrong_side.txt"),
            Map.entry(4, "deep.txt"),
            Map.entry(5, "high.txt"),
            Map.entry(6, "grotte_right.txt"),
            Map.entry(7, "grotte_wrong.txt"),
            Map.entry(8, "hardcore.txt"),
            Map.entry(9, "hardest.txt")
    );

    @Override
    public void start(Stage stage) {
        try (
                final var inputStream = Test.class.getClassLoader().getResourceAsStream(MAP_FILES.get(8));
        ) {
            final var resolver = Resolver.getInstance();
            final var geneticAlgorithm = resolver.loadData(inputStream, 300D, null);
            geneticAlgorithm.compute(resolver.firstGeneration(), HistoryType.ONLY_BEST);
            new Mars(geneticAlgorithm, stage).start();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }
}
