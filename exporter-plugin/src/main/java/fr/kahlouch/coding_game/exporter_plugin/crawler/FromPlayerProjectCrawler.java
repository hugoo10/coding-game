package fr.kahlouch.coding_game.exporter_plugin.crawler;

import fr.kahlouch.coding_game.exporter_plugin.analyzer.GameClass;
import fr.kahlouch.coding_game.exporter_plugin.analyzer.GameImport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FromPlayerProjectCrawler implements ProjectCrawler {
    private static final Path JAVA_FOLDER = Path.of("src", "main", "java");

    @Override
    public String crawl(Path src, String packageName) {
        final var gameClasses = listGameClassRecursively(src.resolve(JAVA_FOLDER));

        final var mergedContents = gameClasses.stream()
                .map(GameClass::content)
                .collect(Collectors.joining("\n"));

        final var mergedImports = gameClasses.stream()
                .map(GameClass::imports)
                .flatMap(Set::stream)
                .filter(gameImport -> !gameImport.matchesPackage(packageName))
                .distinct()
                .map(GameImport::value)
                .map(gi -> "import " + gi + ";")
                .collect(Collectors.joining("\n"));

        return mergedImports + "\n\n" + mergedContents;
    }

    private List<GameClass> listGameClassRecursively(Path src) {
        try (final var filesStream = Files.list(src)) {
            final List<GameClass> gameClasses = new ArrayList<>();
            for (var file : filesStream.toList()) {
                if (Files.isRegularFile(file)) {
                    gameClasses.add(GameClass.read(file));
                } else {
                    gameClasses.addAll(listGameClassRecursively(file));
                }
            }
            return gameClasses;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
