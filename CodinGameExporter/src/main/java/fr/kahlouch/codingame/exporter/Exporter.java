package fr.kahlouch.codingame.exporter;

import fr.kahlouch.codingame.exporter.chooser.SwingDirectoryChooser;
import fr.kahlouch.codingame.exporter.crawler.FromPlayerProjectCrawler;
import fr.kahlouch.codingame.exporter.extractedJar.DependenciesExtractor;
import fr.kahlouch.codingame.exporter.extractedJar.ExtractedJarDirectory;
import fr.kahlouch.codingame.exporter.writer.ClipboardStringWriter;
import fr.kahlouch.codingame.exporter.writer.ConsoleStringWriter;

public class Exporter {
    public static void main(String[] args) {
        final var whiteList = new String[]{"fr\\.kahlouch.*"};
        try (final var tmpDirectory = new ExtractedJarDirectory()) {
            final var dependenciesExtractor = new DependenciesExtractor(tmpDirectory, whiteList);
            new SwingDirectoryChooser().chooseDirectory()
                    .map(path -> {
                        dependenciesExtractor.extractPom(path.getParent());
                        dependenciesExtractor.decompile(tmpDirectory.getPath());
                        return path;
                    })
                    .map(path -> new FromPlayerProjectCrawler().crawl(path, whiteList))
                    .ifPresent(projectAsString -> {
                        new ClipboardStringWriter().write(projectAsString);
                        new ConsoleStringWriter().write(projectAsString);
                    });
        }
    }
}
