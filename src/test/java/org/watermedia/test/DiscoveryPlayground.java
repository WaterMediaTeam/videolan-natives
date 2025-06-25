package org.watermedia.test;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.discovery.providers.IProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.regex.Pattern;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

public class DiscoveryPlayground {
    private static final Pattern ROOT_PATH = Pattern.compile(Platform.isWindows() ? "^[a-z]:\\\\[\\S\\s]+" : "^/\\S+", Pattern.CASE_INSENSITIVE);

    public static void main(String... args) {
        ServiceLoader<IProvider> providers = ServiceLoader.load(IProvider.class);

        List<IProvider> supported = new ArrayList<>(6);
        for (IProvider provider: providers) {
            if (!provider.supported()) continue;
            supported.add(provider);
        }
        if (supported.isEmpty()) throw new RuntimeException("No supported OS");

        // sort
        supported.sort(Comparator.comparingInt(o -> o.priority().ordinal()));

        for (IProvider provider: supported) {
            for (String path: provider.directories()) {
                if (path == null || path.isEmpty()) {
                    LOGGER.warn("Provider '{}' returned an empty path", provider.name());
                    continue;
                }
                path = new File(path).getAbsolutePath();
                if (!ROOT_PATH.matcher(path).find()) {
                    throw new RuntimeException("Root path '" + path + "' is not accordingly designed to work on the current OS");
                }
            }
        }
    }
}