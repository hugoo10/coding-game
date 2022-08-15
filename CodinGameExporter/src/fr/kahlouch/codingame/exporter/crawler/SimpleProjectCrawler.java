package fr.kahlouch.codingame.exporter.crawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SimpleProjectCrawler implements ProjectCrawler {
    private static final List<String> filesToExclude = Arrays.asList(
            ".*Test.*\\.java",
            ".*Mars\\.java",
            ".*Kt.*\\.java",
            ".*GenerationViewer\\.java"
    );

    @Override
    public String crawl(Path path) {
        List<String> javaFilesName = new ArrayList<>();
        importFiles(javaFilesName, path);
        final Set<String> imports = new TreeSet<>();
        final Set<String> packages = new TreeSet<>();
        final List<String> classes = new ArrayList<>();
        for (String javaFileName : javaFilesName) {
            if (javaFileName.endsWith(".java")) {
                StringBuilder classContent = new StringBuilder();
                try (var br = new BufferedReader(new FileReader(javaFileName))) {
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) {
                        if (sCurrentLine.startsWith("import")) {
                            imports.add(sCurrentLine + "\n");
                        } else {
                            if (sCurrentLine.contains("public class")) {
                                sCurrentLine = sCurrentLine.replace("public class", "class");
                            }
                            if (sCurrentLine.contains("public final class")) {
                                sCurrentLine = sCurrentLine.replace("public final class", "class");
                            } else if (sCurrentLine.contains("public abstract")) {
                                sCurrentLine = sCurrentLine.replace("public abstract", "abstract");
                            } else if (sCurrentLine.contains("public interface")) {
                                sCurrentLine = sCurrentLine.replace("public interface", "interface");
                            } else if (sCurrentLine.contains("public enum")) {
                                sCurrentLine = sCurrentLine.replace("public enum", "enum");
                            } else if (sCurrentLine.contains("package")) {
                                packages.add(sCurrentLine.replace("package ", "").replace(";", ""));
                                sCurrentLine = "";
                            }
                            classContent.append(sCurrentLine).append("\n");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                classes.add(classContent.toString());
            }
        }

        final StringBuilder tmp = new StringBuilder();
        imports.stream().filter(importFile -> packages.stream().noneMatch(importFile::contains)).forEach(tmp::append);
        classes.forEach(tmp::append);

        return tmp.toString();
    }

    private static void importFiles(List<String> javaFilesPaths, Path rootFolder) {
        try (final var stream = Files.list(rootFolder)) {
            stream.forEach(subPath -> {
                if (Files.isDirectory(subPath)) {
                    importFiles(javaFilesPaths, subPath);
                } else if (subPath.endsWith(".java") && filesToExclude.stream().noneMatch(fileToExclude -> subPath.toString().matches(fileToExclude))) {
                    if (subPath.toString().equals("Player.java")) {
                        javaFilesPaths.add(0, subPath.toAbsolutePath().toString());
                    } else {
                        javaFilesPaths.add(subPath.toAbsolutePath().toString());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
