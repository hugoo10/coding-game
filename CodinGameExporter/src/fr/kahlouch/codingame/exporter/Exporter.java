package fr.kahlouch.codingame.exporter;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class Exporter {

    private static List<String> filesToExclude = Arrays.asList(".*Test.*\\.java", ".*Mars\\.java", ".*Kt.*\\.java", ".*GenerationViewer\\.java");

    public static void main(String[] args) throws IOException {
        final File projectToExport;
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(".."));
        chooser.setDialogTitle("choose project to import");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            projectToExport = new File(chooser.getSelectedFile().getAbsolutePath() + File.separator + "src");

        } else {
            projectToExport = null;
        }

        if (projectToExport != null) {
            List<String> javaFilesName = new ArrayList<>();
            importFiles(javaFilesName, projectToExport);
            final Set<String> imports = new TreeSet<>();
            final Set<String> packages = new TreeSet<>();
            final List<String> classes = new ArrayList<>();
            for (String javaFileName : javaFilesName) {
                if (javaFileName.endsWith(".java")) {
                    StringBuilder classContent = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(javaFileName));
                    String sCurrentLine;
                    while ((sCurrentLine = br.readLine()) != null) {
                        if (sCurrentLine.startsWith("import")) {
                            imports.add(sCurrentLine + "\n");
                        } else {
                            if (sCurrentLine.contains("public class")) {
                                sCurrentLine = sCurrentLine.replace("public class", "class");
                            } if (sCurrentLine.contains("public final class")) {
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
                    br.close();
                    classes.add(classContent.toString());
                }
            }

            final StringBuilder tmp = new StringBuilder();
            imports.stream().filter(importFile ->  packages.stream().noneMatch(importFile::contains)).forEach(tmp::append);
            classes.forEach(tmp::append);

            String str = tmp.toString();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
            System.out.println(str);
        }
    }

    private static void importFiles(List<String> javaFilesPaths, File rootFolder) {
        if (rootFolder != null) {
            for (File file : rootFolder.listFiles()) {
                if (file.isDirectory()) {
                    importFiles(javaFilesPaths, file);
                } else if (file.getAbsolutePath().endsWith(".java") && filesToExclude.stream().noneMatch(fileToExclude -> file.getAbsolutePath().matches(fileToExclude))) {
                    if (file.getAbsolutePath().endsWith("Player.java")) {
                        javaFilesPaths.add(0, file.getAbsolutePath());
                    } else {
                        javaFilesPaths.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }
}
