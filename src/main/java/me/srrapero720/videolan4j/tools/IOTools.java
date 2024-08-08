package me.srrapero720.videolan4j.tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static me.srrapero720.videolan4j.VideoLan4J.LOGGER;

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
            if (symLink.isDirectory()) {
                LOGGER.debug("Path '{}' is a directory symlink to '{}'", path.toString(), symLink.toPath());
            } else {
                LOGGER.debug("Path '{}' is a file symlink to '{}'", path.toString(), symLink.toPath());
            }
            return symLink;
        } catch (Exception ignored) {}
        return path.toFile();
    }
}
