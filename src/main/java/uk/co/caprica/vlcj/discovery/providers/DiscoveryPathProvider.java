package uk.co.caprica.vlcj.discovery.providers;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import uk.co.caprica.vlcj.discovery.DebugDirectory;
import uk.co.caprica.vlcj.discovery.ProviderPriority;
import uk.co.caprica.vlcj.discovery.strategies.DiscoveryStrategy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uk.co.caprica.vlcj.VideoLan4J.LOGGER;

public abstract class DiscoveryPathProvider {
    private static final Marker IT = MarkerManager.getMarker("DiscoveryProvider");

    public abstract boolean supported();

    public abstract ProviderPriority priority();

    public abstract String[] directories();

    public String name() {
        return getClass().getSimpleName();
    }

    public String find(final DiscoveryStrategy strategy) {
        for (String d: this.directories()) {
            String result = this.find(strategy, d);
            if (result != null) return result;
        }
        return null;
    }

    public String find(final DiscoveryStrategy strategy, final String directory) {
        final File rootDirectory = new File(directory);
        final File[] rootFiles = getFixedFile(rootDirectory.toPath()).listFiles();
        if (rootFiles == null) {
            LOGGER.debug(IT, "Cannot search on path '{}', {}", directory, new DebugDirectory(rootDirectory));
            return null;
        }

        LOGGER.info(IT, "Searching on '{}'", rootDirectory.toString());

        final Pattern[] patterns = strategy.pathPatterns();
        final Set<String> matches = new HashSet<>(patterns.length);

        for (final File child: rootFiles) {
            if (child.isDirectory()) continue; // ignore dirs
            for (Pattern pattern: patterns) {
                Matcher matcher = pattern.matcher(child.getName());
                if (matcher.matches()) {
                    matches.add(pattern.pattern());
                    if (matches.size() == patterns.length) {
                        return directory;
                    }
                }
            }
        }

        matches.clear();

        // TODO: REMOVE
        for (File mainFile: rootFiles) {
            mainFile = getFixedFile(mainFile.toPath());

            final File[] subFolders = mainFile.listFiles();
            if (subFolders == null) return null;

            if (subFolders.length > 16) {
                LOGGER.debug(IT, "Skipped subdirectory '{}', contains more than 16 entries", mainFile.toString());
                continue;
            }
            LOGGER.info(IT, "Searching on subdirectory '{}'", mainFile.toString());
            for (File subFile: subFolders) {
                for (Pattern pattern: patterns) {
                    Matcher matcher = pattern.matcher(subFile.getName());
                    if (matcher.matches()) {
                        // A match was found for this pattern (note that it may be possible to match multiple times, any
                        // one of those matches will do so a Set is used to ignore duplicates)
                        matches.add(pattern.pattern());
                        if (matches.size() == patterns.length) {
                            return mainFile.toPath().toAbsolutePath().toString();
                        }
                    }
                }
            }
        }


        return null;
    }

    /**
     * Returns the real path of the symlink
     * if fails or wasn't a symlink, it returns the exact same path as a File
     * @param path path to the symlink
     * @return File of the real path or the argument-ed path
     */
    private static File getFixedFile(Path path) {
        if (!Files.isSymbolicLink(path)) return path.toFile();
        try {
            File symLink = Files.readSymbolicLink(path).toFile();
            if (symLink.isDirectory()) {
                LOGGER.warn(IT, "Path '{}' is a directory symlink to '{}'", path.toString(), symLink.toPath());
            } else {
                LOGGER.warn(IT, "Path '{}' is a file symlink to '{}'", path.toString(), symLink.toPath());
            }
            return symLink;
        } catch (Exception ignored) {}
        return path.toFile();
    }
}
