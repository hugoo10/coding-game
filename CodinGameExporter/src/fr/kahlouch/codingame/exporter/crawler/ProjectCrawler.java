package fr.kahlouch.codingame.exporter.crawler;

import java.nio.file.Path;

public interface ProjectCrawler {
    String crawl(Path path);
}
