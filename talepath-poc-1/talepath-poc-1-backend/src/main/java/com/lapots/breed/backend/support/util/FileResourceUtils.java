package com.lapots.breed.backend.support.util;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.experimental.UtilityClass;

/**
 * Utils for working with files.
 */
@UtilityClass
public class FileResourceUtils {

    /**
     * Returns top level sub folders. Ignores empty!!!
     * @param folder parent folder
     * @return list of sub folders
     */
    public List<Path> findResourceSubfolders(String folder) {
        try {
            URI uri = FileResourceUtils.class.getResource(folder).toURI();
            return Files
                .list(Paths.get(uri))
                .filter(Files::isDirectory)
                .map(Path::toAbsolutePath)
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns list of files in the folder sorted in natural order.
     * @param folder folder
     * @return list of path files
     */
    public List<Path> listSortedFiles(Path folder) {
        try (Stream<Path> paths = Files.walk(folder)) {
            return paths
                .filter(Files::isRegularFile)
                .map(Path::toAbsolutePath)
                .sorted()
                .collect(Collectors.toList());
        } catch (Exception exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Reads file content from path into string.
     * @param file file
     * @return string content
     */
    public String pathResourceToString(Path file) {
        try {
            return new String(Files.readAllBytes(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
