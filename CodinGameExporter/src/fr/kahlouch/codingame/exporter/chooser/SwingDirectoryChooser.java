package fr.kahlouch.codingame.exporter.chooser;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public class SwingDirectoryChooser implements DirectoryChooser {
    @Override
    public Optional<Path> chooseDirectory() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Choose project to import");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return Optional.of(Path.of(chooser.getSelectedFile().getAbsolutePath(), "src"));
        }
        return Optional.empty();
    }
}
