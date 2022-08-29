package fr.kahlouch.codingame.exporter.extractedJar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public class ExtractedJarDirectory implements AutoCloseable {
    private final Path path;

    public ExtractedJarDirectory() {
        final var homeDirectory = Path.of(System.getenv("userprofile"));
        try {
            this.path = Files.createTempDirectory(homeDirectory, null);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void close() {
        try (Stream<Path> walk = Files.walk(path)) {
            walk.sorted(Comparator.reverseOrder())
                    .forEach(toDelete -> {
                        try {
                            Files.deleteIfExists(toDelete);
                        } catch (IOException ioe) {
                            throw new RuntimeException(ioe);
                        }
                    });
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
