package fr.kahlouch.codingame.exporter.crawler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class FromPlayerProjectCrawler implements ProjectCrawler {
    @Override
    public String crawl(Path src) {
        final var files = new HashSet<Path>();
        final var player = findFile(Path.of("Player.java"), src);
        var file = player.orElseThrow();
        files.add(file);
        final var imports = extractImports(file);

        return null;
    }

    private Optional<Path> findFile(Path file, Path root) {
        try (final var matches = Files.find(root, 99, (p, bfa) -> p.endsWith(file))) {
            return matches.findFirst();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Path> extractImports(Path file){
        try(final var lines = Files.lines(file)){
            return lines.filter(line -> line.matches("import fr\\.kahlouch.*"))
                    .map(path -> path.replace("import ", "").replace(";",""))
                    .map(path -> path.split("\\."))
                    .map(path -> Path.of(path[0], Arrays.copyOfRange(path, 1, path.length)))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
