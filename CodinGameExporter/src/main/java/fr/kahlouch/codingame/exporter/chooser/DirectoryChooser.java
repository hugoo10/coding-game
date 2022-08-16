package fr.kahlouch.codingame.exporter.chooser;

import java.nio.file.Path;
import java.util.Optional;

public interface DirectoryChooser {
    Optional<Path> chooseDirectory();
}
