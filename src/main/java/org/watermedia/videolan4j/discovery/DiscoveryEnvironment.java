package org.watermedia.videolan4j.discovery;

import com.sun.jna.NativeLibrary;
import org.watermedia.videolan4j.VideoLan4J;
import org.watermedia.videolan4j.binding.lib.LibC;
import org.watermedia.videolan4j.tools.IOTools;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

public abstract class DiscoveryEnvironment {
    private static final ServiceLoader<DiscoveryProvider> PROVIDERS = ServiceLoader.load(DiscoveryProvider.class);
    private static final ServiceLoader<DiscoveryEnvironment> STRATEGIES = ServiceLoader.load(DiscoveryEnvironment.class);
    private static final Marker IT = MarkerManager.getMarker("DiscoveryProvider");
    public static final String PLUGIN_ENV_NAME = "VLC_PLUGIN_PATH";

    public abstract boolean supported();

    public abstract Pattern[] binPatterns();

    public abstract String[] pluginPaths();

    public String name() {
        return getClass().getSimpleName();
    }

    static List<DiscoveryEnvironment> getStrategies() {
        Iterator<DiscoveryEnvironment> i = STRATEGIES.iterator();
        List<DiscoveryEnvironment> result = new ArrayList<>();

        while (i.hasNext()) {
            DiscoveryEnvironment e = i.next();
            if (e.supported()) result.add(e);
        }

        return result;
    }

    static List<DiscoveryProvider> getProviders() {
        Iterator<DiscoveryProvider> i = PROVIDERS.iterator();
        List<DiscoveryProvider> result = new ArrayList<>();

        while (i.hasNext()) {
            DiscoveryProvider e = i.next();
            if (e.supported()) result.add(e);
        }

        // Sorting always using ProviderPriority
        result.sort(Comparator.comparing(DiscoveryProvider::priority));

        return result;
    }

    String find(final String directory) {
        final File rootDirectory = new File(directory);
        final File[] rootFiles = IOTools.getFixedFile(rootDirectory.toPath()).listFiles();
        if (rootFiles == null) {
            LOGGER.debug(IT, "Cannot search on path '{}', {}", directory, new DebugDirectory(rootDirectory));
            return null;
        }

        LOGGER.info(IT, "Searching on '{}'", rootDirectory.toString());

        final Pattern[] patterns = this.binPatterns();
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
        return null;
    }

    protected boolean onFound(DiscoveryProvider provider, String path) {
        NativeLibrary.addSearchPath(VideoLan4J.LIBVLC_NAME, path);
        String env = System.getenv(PLUGIN_ENV_NAME);
        if (env == null || env.isEmpty()) {
            return this.setPluginPath(path);
        }
        return true;
    }

    protected boolean setPluginPath(String path) {
        File f = new File(path);
        for (String pluginsPath: pluginPaths()) {
            Path p = f.toPath().resolve(pluginsPath);
            if (p.toFile().exists()) {
                return LibC.INSTANCE.setenv(PLUGIN_ENV_NAME, p.toString(), 1) == 0;
            }
        }

        return false;
    }

    public static class DebugDirectory {
        private final String path;
        private final boolean exists;
        private final boolean directory;
        private final boolean readable;
        private final boolean executable;
        private final boolean hidden;

        public DebugDirectory(File file) {
            this.path = file.toPath().toString();
            this.exists = file.exists();
            this.directory = file.isDirectory();
            this.readable = file.canRead();
            this.executable = file.canExecute();
            this.hidden = file.isHidden();
        }

        @Override
        public String toString() {
            return "DebugDirectory{" +
                    "path='" + path + '\'' +
                    ", exists=" + exists +
                    ", directory=" + directory +
                    ", readable=" + readable +
                    ", executable=" + executable +
                    ", hidden=" + hidden +
                    '}';
        }
    }
}
