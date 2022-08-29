package fr.kahlouch.codingame.exporter;

import fr.kahlouch.codingame.exporter.extractedJar.ExtractedJarDirectory;

import java.io.IOException;

public class Exporter {
    public static void main(String[] args){
        try(final var tmpDirectory = new ExtractedJarDirectory()) {
            ProcessBuilder pb = new ProcessBuilder("jar", "xf", "C:\\Users\\hugok\\.m2\\repository\\fr\\kahlouch\\Genetic\\2.0.1\\Genetic-2.0.1.jar");
            pb.directory(tmpDirectory.getPath().toFile());
            pb.start().waitFor();
            //final PrintWriter writer = new PrintWriter(System.out);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //%userprofile%


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
