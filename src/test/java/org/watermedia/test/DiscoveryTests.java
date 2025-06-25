package org.watermedia.test;

import com.sun.jna.Platform;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.watermedia.videolan4j.VideoLan4J;
import org.watermedia.videolan4j.discovery.NativeDiscovery;
import org.watermedia.videolan4j.discovery.providers.IProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.regex.Pattern;


import static org.junit.jupiter.api.Assertions.*;
import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

public class DiscoveryTests {
    private static final Marker IT = MarkerManager.getMarker(DiscoveryTests.class.getSimpleName());
    private static final Pattern ROOT_PATH = Pattern.compile(Platform.isWindows() ? "^[a-z]:\\\\[\\S\\s]+" : "^/\\S+", Pattern.CASE_INSENSITIVE);

    @Test
    public void test$checkClassLoaderAndNativeSkipping() {
        VideoLan4J.checkClassLoader(Thread.currentThread().getContextClassLoader());
    }

    @Test
    public void test$pathLookup() {
        ServiceLoader<IProvider> providers = ServiceLoader.load(IProvider.class);

        List<IProvider> supported = new ArrayList<>(6);
        for (IProvider provider: providers) {
            if (!provider.supported()) continue;
            supported.add(provider);
        }
        assertFalse(supported.isEmpty(), "Zero-supported providers");

        // sort
        supported.sort(Comparator.comparingInt(o -> o.priority().ordinal()));

        for (IProvider provider: supported) {
            for (String path: provider.directories()) {
                if (path == null || path.isEmpty()) {
                    LOGGER.warn(IT, "Provider '{}' returned an empty path", provider.name());
                    continue;
                }
                path = new File(path).getAbsolutePath();
                assertTrue(ROOT_PATH.matcher(path).find(), "Root path '" + path + "' is not accordingly designed to work on the current OS");
            }
        }
    }

    @AfterAll
    public static void test$discovery() {
        assertTrue(NativeDiscovery.start());
    }
}
