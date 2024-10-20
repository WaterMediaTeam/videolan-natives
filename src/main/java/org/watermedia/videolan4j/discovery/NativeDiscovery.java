package org.watermedia.videolan4j.discovery;

import com.sun.jna.NativeLibrary;
import com.sun.jna.StringArray;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.watermedia.videolan4j.VideoLan4J;
import org.watermedia.videolan4j.binding.internal.libvlc_instance_t;
import org.watermedia.videolan4j.binding.lib.LibVlc;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.*;

public class NativeDiscovery {
    private static final Marker IT = MarkerManager.getMarker("NativeDiscovery");

    private static boolean discovered = false;
    private static DiscoveryEnvironment activeStrategy;
    private static String discoveredPath;

    public static boolean isDiscovered() {
        return discovered;
    }

    public static DiscoveryEnvironment getActiveStrategy() {
        return activeStrategy;
    }

    public static String getDiscoveredPath() {
        return discoveredPath;
    }

    public static boolean discovery() {
        if (discovered) return true;

        for (DiscoveryEnvironment environment: DiscoveryEnvironment.getStrategies()) {

            String directory = null;
            DiscoveryProvider provider = null;
            for (DiscoveryProvider p: DiscoveryEnvironment.getProviders()) {
                for (String d: p.directories()) {
                    directory = environment.find(d);
                    provider = p;
                    if (directory != null) break;
                }
            }

            if (directory == null) continue;

            if (environment.onFound(provider, directory) && testInstance()) {
                activeStrategy = environment;
                discoveredPath = directory;
                discovered = true;
                VideoLan4J.LOGGER.info(IT, "Founded VLC {} on '{}', using '{}/{}'", VideoLan4J.getVideoLanVersion(), directory, environment.name(), provider.name());
                return true;
            } else {
                VideoLan4J.LOGGER.error(IT, "Failed loading VLC in '{}' using '{}/{}' cleaning JNA paths and trying again...", directory, environment.name(), provider.name());
                if (testCleanup()) continue;
                return false;
            }
        }

        return false;
    }


    private static boolean testInstance() {
        try {
            libvlc_instance_t instance = LibVlc.libvlc_new(0, new StringArray(new String[0]));
            if (instance == null)
                return false;

            LibVlc.libvlc_release(instance);
            if (VideoLan4J.getVideoLanVersion().atLeast(VideoLan4J.LIBVLC_MIN_VERSION)) {
                return true;
            }
        } catch (Error e) {
            VideoLan4J.LOGGER.error(IT, "Failed to attempt load VLC instance", e);
        }
        return false;
    }

    public static boolean testCleanup() {
        try {
            Field searchPaths = NativeLibrary.class.getDeclaredField("searchPaths");
            searchPaths.setAccessible(true);

            Field libraries = NativeLibrary.class.getDeclaredField("libraries");
            libraries.setAccessible(true);

            Map<String, Reference<NativeLibrary>> libs = (Map<String, Reference<NativeLibrary>>) libraries.get(null);
            Map<String, List<String>> paths = (Map<String, List<String>>) searchPaths.get(null);
            libs.remove(VideoLan4J.LIBVLC_NAME);
            libs.remove(VideoLan4J.LIBVLCCORE_NAME);
            paths.remove(VideoLan4J.LIBVLC_NAME);
            paths.remove(VideoLan4J.LIBVLCCORE_NAME);
            return true;
        } catch (Exception e) {
            VideoLan4J.LOGGER.error(IT, "Failed to attempt clean broken discovered path", e);
        }
        return false;
    }
}
