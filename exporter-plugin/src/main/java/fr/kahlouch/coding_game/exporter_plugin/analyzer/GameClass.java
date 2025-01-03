package fr.kahlouch.coding_game.exporter_plugin.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public record GameClass(Set<GameImport> imports, String content) {
    private static final String PACKAGE = "package";
    private static final Pattern CLASS_MODIFIER = Pattern.compile(".*(class|interface|record|enum)(\\s.+)");


    public static GameClass read(Path path) {
        final Set<GameImport> imports = new HashSet<>();
        final var contentBuilder = new StringBuilder();

        try {
            final var allLines = Files.readAllLines(path);
            for (var line : allLines) {

                if (line.startsWith(PACKAGE)) continue;

                final var importOpt = GameImport.parseIfMatches(line);
                if (importOpt.isPresent()) {
                    imports.add(importOpt.get());
                    continue;
                }
                contentBuilder.append(removeClassModifiers(line)).append('\n');
            }
            return new GameClass(imports, contentBuilder.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String removeClassModifiers(String line) {
        final var patternMatcher = CLASS_MODIFIER.matcher(line);
        if (patternMatcher.find()) {
            return patternMatcher.group(1) + patternMatcher.group(2);
        }
        return line;
    }
}
