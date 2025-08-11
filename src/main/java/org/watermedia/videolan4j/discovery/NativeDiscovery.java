package org.watermedia.videolan4j.discovery;

import com.sun.jna.NativeLibrary;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.watermedia.videolan4j.VideoLan4J;
import org.watermedia.videolan4j.binding.internal.libvlc_instance_t;
import org.watermedia.videolan4j.discovery.providers.IProvider;
import org.watermedia.videolan4j.tools.Tools;

import java.io.File;
import java.lang.ref.Reference;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

public final class NativeDiscovery {
    private static final Marker IT = MarkerManager.getMarker(NativeDiscovery.class.getSimpleName());
    private static final ServiceLoader<IProvider> PROVIDERS = ServiceLoader.load(IProvider.class);
    private static Map<String, Reference<NativeLibrary>> jnaLibraries;
    private static Map<String, List<String>> jnaSearchPaths;

    private static boolean discovered = false;
    private static boolean attempted = false;
    private static String discoveredPath;

    /**
     * Checks if VLC binaries are founded by the discovery, this doesn't mean
     * VLC is not founded, JNA can automatically found VLC binaries on default paths
     */
    public static boolean discovered() {
        return discovered;
    }

    public static String discoveryPath() {
        return discoveredPath;
    }

    public static synchronized boolean start() {
        if (discovered) return true;
        if (attempted) return false;

        // environment is determinist, C++ compiled code is not "java like"
        final Environment env = Environment.get();
        if (env == null) {
            LOGGER.info(IT, "Unsupported environment '{}'", Environment.osName());
            attempted = true;
            return false;
        }

        // iterate providers
        for (final IProvider provider: getProviders()) {
            LOGGER.info(IT, "Searching using '{}'", provider.name());

            // iterate all directories
            for (final String d: provider.directories()) {
                final String directory = start$searchPath(env, d);

                // keep searching
                if (directory == null) continue;

                // on found
                if (setSearchPath(env, directory)) {
                    LOGGER.info(IT, "Founded VLC binaries in '{}' using '{}', running test...", directory, provider.name());
                    if (testInstance()) {
                        discoveredPath = directory;
                        discovered = true;
                        LOGGER.info(IT, "Successfully loaded VLC {} in '{}' using '{}'", VideoLan4J.getLibVersion(), directory, provider.name());
                        return true;
                    // Explicit failed to load
                    } else {
                        LOGGER.error(IT, "Failed to load VLC in '{}' using '{}'", directory, provider.name());
                        break;
                    }
                // Failed to set the search path... missing plugins' path?
                } else {
                    LOGGER.error(IT, "Failed to set search path for VLC in '{}' using '{}'", directory, provider.name());
                    break;
                }
            }
        }

        attempted = true;
        return false;
    }

    private static String start$searchPath(final Environment env, final String directory) {
        final File rootDirectory = new File(directory);
        final File[] rootFiles = Tools.getRealFile(rootDirectory.toPath()).listFiles();
        if (rootFiles == null) {
            LOGGER.debug(IT, "Cannot search on path '{}', {}", directory, new DebugDirectory(rootDirectory));
            return null;
        }

        LOGGER.info(IT, "Searching on '{}'", rootDirectory.toString());

        final Pattern[] patterns = env.getFilePatterns();
        final Set<String> matches = new HashSet<>(patterns.length);

        for (final File child: rootFiles) {
            if (child.isDirectory()) {
                if (child.getName().toLowerCase().contains("vlc") /* weak check */ || child.getName().toLowerCase().contains("bin") || child.getName().toLowerCase().contains("lib")) {
                    final String r = start$searchPath(env, child.getAbsolutePath());
                    if (r != null) return r;
                }
                continue; // ignore dirs
            }
            for (final Pattern pattern: patterns) {
                final Matcher matcher = pattern.matcher(child.getName());
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

    private static boolean setSearchPath(final Environment env, final String path) {
        NativeLibrary.addSearchPath(VideoLan4J.LIBVLC_NAME, path);
        NativeLibrary.addSearchPath(VideoLan4J.LIBVLCCORE_NAME, path);

        final boolean success = setPluginPath(env, path);
        // on macOS, we need to preload vlccore, otherwise it fails to load ALWAYS
        if (env == Environment.MACOS) {
            LOGGER.debug(IT, "Running on MACOS, preloading vlccore");
            NativeLibrary.getInstance(VideoLan4J.LIBVLCCORE_NAME);
        }
        return success;
    }

    private static boolean setPluginPath(final Environment env, final String path) {
        final File f = new File(path);
        final String[] pluginsPaths = env.getPluginPaths();
        if (pluginsPaths == null || pluginsPaths.length == 0) {
            LOGGER.error(IT, "No plugins paths defined for environment '{}'", env.name());
            return false;
        }
        for (final String pluginsPath: pluginsPaths) {
            final File p = f.toPath().resolve(pluginsPath).toAbsolutePath().toFile().getAbsoluteFile();
            if (p.exists() && p.isDirectory() && p.canRead() && p.canExecute()) {
                LOGGER.info(IT, "Setting plugins path to '{}'", p.toString());
                return env.setVar(VideoLan4J.LIBVLC_PLUGIN_ENV_NAME, p.toString());
            } else {
                LOGGER.error(IT, "Plugins path '{}' doesn't exist or cannot be accessed, {}", p.toString(), new DebugDirectory(p));
            }
        }

        LOGGER.error(IT, "Plugins path doesn't exist");
        return false;
    }

    private static boolean testInstance() {
        try {
            final libvlc_instance_t instance = VideoLan4J.createInstance(
                    "--no-quiet",
                    "--log-verbose=3",
                    "--file-logging",
                    "--logfile=" + new File("logs/videolan-discovery.log").getAbsolutePath(),
                    "--verbose=2"
            );

            if (instance == null) {
                LOGGER.error(IT, "Failed to create VLC test instance, printing log file");
                final File logFile = new File("logs/videolan-discovery.log");
                if (logFile.exists() && logFile.canRead()) {
                    final String logContent = Tools.readStringSafe(logFile.toPath());
                    if (logContent != null && !logContent.isEmpty()) {
                        final String[] lines = logContent.split("\n");
                        LOGGER.error(IT, "VLC log file content START ------------");
                        for (final String line: lines) {
                            LOGGER.error(IT, line);
                        }
                        LOGGER.error(IT, "VLC log file content END ------------");
                    } else {
                        LOGGER.error(IT, "VLC log file is empty or cannot be read, {}", new DebugDirectory(logFile));
                    }

                } else {
                    LOGGER.error(IT, "VLC log file doesn't exist or cannot be read, {}", new DebugDirectory(logFile));
                }
                LOGGER.error(IT, "VLC test instance is null, cannot continue discovery");
                return false;
            }

            VideoLan4J.releaseInstance(instance);

            if (VideoLan4J.isLibSupported()) {
                LOGGER.info(IT, "VLC test instance created successfully");
                return true;
            } else {
                LOGGER.error(IT, "VLC {} is not supported, supported versions are between {} and {}", VideoLan4J.getLibVersion(), VideoLan4J.LIBVLC_MIN_VERSION, VideoLan4J.LIBVLC_MAX_VERSION);
            }
        } catch (final Error e) {
            LOGGER.error(IT, "Failed to create and test VLC instance", e);
        }
        return false;
    }


    private static List<IProvider> getProviders() {
        final Iterator<IProvider> i = PROVIDERS.iterator();
        final List<IProvider> result = new ArrayList<>();

        while (i.hasNext()) {
            final IProvider e = i.next();
            if (e.supported()) result.add(e);
        }

        // Sorting always using ProviderPriority
        result.sort(Comparator.comparing(IProvider::priority));

        return result;
    }

    private static final class DebugDirectory {
        private final String path;
        private final boolean exists;
        private final boolean directory;
        private final boolean readable;
        private final boolean executable;
        private final boolean hidden;

        public DebugDirectory(final File file) {
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
                    "path='" + this.path + '\'' +
                    ", exists=" + this.exists +
                    ", directory=" + this.directory +
                    ", readable=" + this.readable +
                    ", executable=" + this.executable +
                    ", hidden=" + this.hidden +
                    '}';
        }
    }
}
