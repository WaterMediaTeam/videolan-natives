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
import java.lang.reflect.Field;
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
        for (IProvider provider: getProviders()) {
            LOGGER.info(IT, "Searching using '{}'", provider.name());

            // iterate all directories
            for (String d: provider.directories()) {
                String directory = start$searchPath(env, d);

                // keep searching
                if (directory == null) continue;

                // on found
                if (setSearchPath(env, directory)) {
                    if (testInstance()) {
                        discoveredPath = directory;
                        discovered = true;
                        LOGGER.info(IT, "Founded VLC {} in '{}' using '{}'", VideoLan4J.getLibVersion(), directory, provider.name());
                        return true;
                    // Explicit failed to load
                    } else {
                        LOGGER.error(IT, "Failed to load VLC in '{}' using '{}'", directory, provider.name());
                        if (testCleanup()) continue;
                        break;
                    }
                // Failed to set the search path... missing plugins' path?
                } else {
                    LOGGER.error(IT, "Failed to set search path for VLC in '{}' using '{}'", directory, provider.name());
                    if (testCleanup()) continue;
                    break;
                }
            }
        }

        attempted = true;
        return false;
    }

    private static String start$searchPath(final Environment env, final String directory) {
        final File rootDirectory = new File(directory.endsWith(File.separatorChar + "bin") || directory.endsWith(File.separatorChar + "lib") ? directory : directory.substring(0, directory.lastIndexOf(File.separatorChar)));
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
                if (child.getName().toLowerCase().contains("vlc") || child.getName().toLowerCase().contains("bin") || child.getName().toLowerCase().contains("lib")) {
                    String r = start$searchPath(env, child.getAbsolutePath());
                    if (r != null) return r;
                }
                continue; // ignore dirs
            }
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

    private static boolean setSearchPath(Environment env, String path) {
        NativeLibrary.addSearchPath(VideoLan4J.LIBVLC_NAME, path);
        // MAC WORKAROUND: PRELOADS VLCCore
        if (env == Environment.MACOS) {
            NativeLibrary.addSearchPath(VideoLan4J.LIBVLCCORE_NAME, path);
            NativeLibrary.getInstance(VideoLan4J.LIBVLCCORE_NAME);
        }
        String pluginPath = System.getenv(VideoLan4J.LIBVLC_PLUGIN_ENV_NAME);
        if (pluginPath == null || pluginPath.isEmpty()) {
            return setPluginPath(env, path);
        }
        return true;
    }

    private static boolean setPluginPath(Environment env, String path) {
        File f = new File(path);
        for (String pluginsPath: env.getPluginPaths()) {
            File p = f.toPath().resolve(pluginsPath).toAbsolutePath().toFile().getAbsoluteFile();
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
            libvlc_instance_t instance = VideoLan4J.createInstance(
                    "--no-quiet",
                    "--log-verbose=3",
                    "--file-logging",
                    "--logfile=" + new File("logs/videolan-discovery.log").getAbsolutePath(),
                    "--verbose=2"
            );

            if (instance == null)
                return false;

            VideoLan4J.releaseInstance(instance);

            if (VideoLan4J.isLibSupported()) {
                return true;
            }
        } catch (Error e) {
            LOGGER.error(IT, "Failed to attempt create VLC instance", e);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static boolean testCleanup() {
        try {
            if (jnaSearchPaths == null) {
                Field searchPaths = NativeLibrary.class.getDeclaredField("searchPaths");
                searchPaths.setAccessible(true);
                jnaSearchPaths = (Map<String, List<String>>) searchPaths.get(null);
            }
            if (jnaLibraries == null) {
                Field libraries = NativeLibrary.class.getDeclaredField("libraries");
                libraries.setAccessible(true);
                jnaLibraries = (Map<String, Reference<NativeLibrary>>) libraries.get(null);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error(IT, "Failed to clean JNA search paths, search must be stopped!", e);
        }
        return false;
    }

    private static List<IProvider> getProviders() {
        Iterator<IProvider> i = PROVIDERS.iterator();
        List<IProvider> result = new ArrayList<>();

        while (i.hasNext()) {
            IProvider e = i.next();
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
