package fr.kahlouch.coding_game.exporter_plugin.analyzer;

import java.util.Optional;
import java.util.regex.Pattern;

public record GameImport(String value) {
    private static final Pattern IMPORT_PATTERN = Pattern.compile("import ([^;]+);");

    public static Optional<GameImport> parseIfMatches(String line) {
        final var patternMatcher = IMPORT_PATTERN.matcher(line);
        if (patternMatcher.matches()) {
            return Optional.of(new GameImport(patternMatcher.group(1)));
        }
        return Optional.empty();
    }

    public boolean matchesPackage(String packageName) {
        return value.startsWith(packageName);
    }
}
