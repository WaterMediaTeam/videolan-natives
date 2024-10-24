package org.watermedia.videolan4j.tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

public class IOTools {
    /**
     * Returns the real path of the symlink
     * if fails or wasn't a symlink, it returns the exact same path as a File
     * @param path path to the symlink
     * @return File of the real path or the argument-ed path
     */
    public static File getFixedFile(Path path) {
        if (!Files.isSymbolicLink(path)) return path.toFile();
        try {
            File symLink = Files.readSymbolicLink(path).toFile();
            LOGGER.debug("Path '{}' is a {} symlink to '{}'", path.toString(), symLink.isDirectory() ? "directory" : "file", symLink.toPath());
            return symLink;
        } catch (Exception ignored) {}
        return path.toFile();
    }
}
