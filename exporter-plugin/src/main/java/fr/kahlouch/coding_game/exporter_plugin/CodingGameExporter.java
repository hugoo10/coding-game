package fr.kahlouch.coding_game.exporter_plugin;

import fr.kahlouch.coding_game.exporter_plugin.crawler.FromPlayerProjectCrawler;
import fr.kahlouch.coding_game.exporter_plugin.writer.ClipboardStringWriter;
import fr.kahlouch.coding_game.exporter_plugin.writer.ConsoleStringWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "coding-game-exporter", defaultPhase = LifecyclePhase.COMPILE)
public class CodingGameExporter extends AbstractMojo {
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;
    @Parameter(name = "project-package")
    String projectPackage;

    @Override
    public void execute() {
        String projectAsString = new FromPlayerProjectCrawler().crawl(project.getBasedir().toPath(), projectPackage);
        System.out.println("Exporting Codingame to clipboard...");
        new ClipboardStringWriter().write(projectAsString);
        new ConsoleStringWriter().write(projectAsString);
    }
}
