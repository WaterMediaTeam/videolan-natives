package me.srrapero720.videolan4j.discovery.strategies;

import com.sun.jna.NativeLibrary;
import me.srrapero720.videolan4j.discovery.providers.DiscoveryPathProvider;
import me.srrapero720.videolan4j.VideoLan4J;
import me.srrapero720.videolan4j.binding.lib.LibC;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public abstract class DiscoveryStrategy {
    private static final ServiceLoader<DiscoveryPathProvider> PROVIDERS = ServiceLoader.load(DiscoveryPathProvider.class);
    public static final String PLUGIN_ENV_NAME = "VLC_PLUGIN_PATH";

    public static List<DiscoveryPathProvider> getSupportedProviders() {
        Iterator<DiscoveryPathProvider> i = PROVIDERS.iterator();
        List<DiscoveryPathProvider> result = new ArrayList<>();

        while (i.hasNext()) {
            DiscoveryPathProvider e = i.next();
            if (e.supported()) result.add(e);
        }

        // Sorting always using ProviderPriority
        result.sort(Comparator.comparing(DiscoveryPathProvider::priority));

        return result;
    }

    public String name() {
        return getClass().getSimpleName();
    }

    public abstract boolean supported();

    public abstract Pattern[] pathPatterns();

    public abstract String[] pluginPaths();

    public boolean onFound(DiscoveryPathProvider provider, String path) {
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
}
