package fr.kahlouch.codingame.exporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Exporter {
    public static void main(String[] args) throws IOException {
        //%userprofile%
        ProcessBuilder pb = new ProcessBuilder("jar", "xf", ".\\Genetic-2.0.1.jar");
        pb.directory(new File("C:\\Users\\hugok\\.m2\\repository\\fr\\kahlouch\\Genetic\\2.0.1"));
        pb.start();
        final PrintWriter writer = new PrintWriter(System.out);

       /* try {
            Decompiler.decompile(
                    "C:\\Users\\hugok\\.m2\\repository\\fr\\kahlouch\\Genetic\\2.0.1\\Genetic-2.0.1.jar",
                    new com.strobel.decompiler.PlainTextOutput(writer)
            );
        }
        finally {
            writer.flush();
        }*/
        /*new SwingDirectoryChooser().chooseDirectory()
                .map(path -> new FromPlayerProjectCrawler().crawl(path))
                .ifPresent(projectAsString -> {
                    new ClipboardStringWriter().write(projectAsString);
                    new ConsoleStringWriter().write(projectAsString);
                });*/
    }
}
