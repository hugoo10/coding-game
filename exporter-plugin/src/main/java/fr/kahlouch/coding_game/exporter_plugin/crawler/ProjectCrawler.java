package fr.kahlouch.coding_game.exporter_plugin.crawler;

import java.nio.file.Path;

public interface ProjectCrawler {
    String crawl(Path path, String packageName);
}
