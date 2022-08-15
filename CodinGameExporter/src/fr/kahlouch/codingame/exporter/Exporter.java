package fr.kahlouch.codingame.exporter;

import fr.kahlouch.codingame.exporter.chooser.SwingDirectoryChooser;
import fr.kahlouch.codingame.exporter.crawler.SimpleProjectCrawler;
import fr.kahlouch.codingame.exporter.writer.ClipboardStringWriter;
import fr.kahlouch.codingame.exporter.writer.ConsoleStringWriter;

public class Exporter {
    public static void main(String[] args) {
        new SwingDirectoryChooser().chooseDirectory()
                .map(path -> new SimpleProjectCrawler().crawl(path))
                .ifPresent(projectAsString -> {
                    new ClipboardStringWriter().write(projectAsString);
                    new ConsoleStringWriter().write(projectAsString);
                });
    }
}
