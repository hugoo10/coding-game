package fr.kahlouch.codingame.exporter.extractedJar;


import com.strobel.decompiler.Decompiler;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class DependenciesExtractor {
    private final static String MAVEN_REPOSITORY = Path.of(System.getenv("userprofile"), ".m2", "repository").toString();
    private final Map<String, Map<String, String>> dependencies;
    private final List<String> dependencyGroupsWhiteList;
    private final ExtractedJarDirectory extractedJarDirectory;

    public DependenciesExtractor(ExtractedJarDirectory extractedJarDirectory, String... dependencyGroupsWhiteList) {
        this.dependencyGroupsWhiteList = Arrays.asList(dependencyGroupsWhiteList);
        this.dependencies = new HashMap<>();
        this.extractedJarDirectory = extractedJarDirectory;
    }

    public void extractPom(Path projectPath) {
        findPom(projectPath)
                .map(this::extractDependencies)
                .stream()
                .flatMap(Collection::stream)
                .filter(this::isValidDependency)
                .map(this::getDepencencyPath)
                .map(this::extractJar)
                .forEach(this::extractPom);
    }

    private Optional<Path> findPom(Path projectPath) {
        try (final var searchResult = Files.find(
                projectPath,
                10,
                (path, basicFileAttributes) -> path.getFileName().toString().equals("pom.xml")
        )) {
            return searchResult.findFirst();
        } catch (IOException ioe) {
            return Optional.empty();
        }
    }

    private List<Dependency> extractDependencies(Path pomPath) {
        try {
            final var reader = new MavenXpp3Reader();
            final var model = reader.read(new FileReader(pomPath.toFile()));
            return model.getDependencies();
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidDependency(Dependency dependency) {
        return this.dependencyGroupsWhiteList.stream().anyMatch(groupRegex -> dependency.getGroupId().matches(groupRegex));
    }

    private Path getDepencencyPath(Dependency dependency) {
        final var pathOfGroupInRepository = String.join("\\", dependency.getGroupId().split("\\."));
        return Path.of(MAVEN_REPOSITORY, pathOfGroupInRepository, dependency.getArtifactId(), dependency.getVersion(), String.format("%s-%s.jar", dependency.getArtifactId(), dependency.getVersion()));
    }

    private Path extractJar(Path dependencyPath) {
        try {
            final var pb = new ProcessBuilder("jar", "xf", dependencyPath.toAbsolutePath().toString());
            pb.directory(this.extractedJarDirectory.getPath().toFile());
            pb.start().waitFor();
            return this.extractedJarDirectory.getPath().resolve(dependencyPath.toString().substring(MAVEN_REPOSITORY.length() + 1)).getParent().getParent();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void decompile(Path projectPath) {
        try (Stream<Path> walk = Files.walk(projectPath)) {
            walk.filter(file -> file.toString().matches("^.*\\.class$"))
                    .forEach(toDecompile -> {
                        try (final var fileWriter =  new FileWriter(toDecompile.toAbsolutePath().toString().replace(".class", ".java"));){
                            Decompiler.decompile(
                                    toDecompile.toAbsolutePath().toString(),
                                    new com.strobel.decompiler.PlainTextOutput(fileWriter)
                            );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
