package me.srrapero720.videolan4j.discovery;

import com.sun.jna.NativeLibrary;
import com.sun.jna.StringArray;
import me.srrapero720.videolan4j.discovery.providers.DiscoveryPathProvider;
import me.srrapero720.videolan4j.discovery.strategies.DiscoveryStrategy;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import me.srrapero720.videolan4j.Version;
import me.srrapero720.videolan4j.VideoLan4J;
import me.srrapero720.videolan4j.binding.internal.libvlc_instance_t;
import me.srrapero720.videolan4j.binding.lib.LibVlc;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.util.*;

import static me.srrapero720.videolan4j.VideoLan4J.LOGGER;

public class NativeDiscovery {
    private static final ServiceLoader<DiscoveryStrategy> STRATEGIES = ServiceLoader.load(DiscoveryStrategy.class);
    private static final Marker IT = MarkerManager.getMarker("NativeDiscovery");

    private static boolean discovered = false;
    private static DiscoveryStrategy activeStrategy;
    private static String discoveredPath;

    public static boolean isDiscovered() {
        return discovered;
    }

    public static DiscoveryStrategy getActiveStrategy() {
        return activeStrategy;
    }

    public static String getDiscoveredPath() {
        return discoveredPath;
    }

    public static boolean discovery() {
        if (discovered) return true;

        for (DiscoveryStrategy strategy: STRATEGIES) {
            if (!strategy.supported()) continue;

            String directory = null;
            DiscoveryPathProvider provider = null;
            for (DiscoveryPathProvider p: DiscoveryStrategy.getSupportedProviders()) {
                directory = p.find(strategy);
                provider = p;
                if (directory != null) break;
            }

            if (directory == null) continue;

            if (strategy.onFound(provider, directory) && attemptLoad()) {
                activeStrategy = strategy;
                discoveredPath = directory;
                discovered = true;
                LOGGER.info(IT, "Founded VLC {} on '{}', using '{}/{}'", VideoLan4J.getVideoLanVersion(), directory, strategy.name(), provider.name());
                return true;
            } else {
                LOGGER.error(IT, "Failed loading VLC in '{}' using '{}/{}' cleaning JNA and trying again...", directory, strategy.name(), provider.name());
                if (attemptFix()) continue;
                return false;
            }
        }

        return false;
    }


    private static boolean attemptLoad() {
        try {
            libvlc_instance_t instance = LibVlc.libvlc_new(0, new StringArray(new String[0]));
            if (instance != null) {
                LibVlc.libvlc_release(instance);
                Version current = new Version(LibVlc.libvlc_get_version());
                if (current.atLeast(VideoLan4J.LIBVLC_MIN_VERSION)) {
                    return true;
                }
            }
        } catch (Error e) {
            // The library could not be loaded, this includes NoClassDefFoundError which would be thrown e.g. if there
            // was a direct-mapped method in the LibVlc class that was missing from the loaded native library - we don't
            // report the error here (since this discovery is optional), it will be reported by the factory subsequently

            // This message should display the reason the native library could not be bound, specifically if the library
            // binding failed due to an undefined symbol, it should be displayed here
            LOGGER.error(IT, "Attempt to load a VLC instance was failed", e);
        }
        return false;
    }


    private static Field searchPaths;
    private static Field libraries;
    public static boolean attemptFix() {
        try {
            if (searchPaths == null) {
                searchPaths = NativeLibrary.class.getDeclaredField("searchPaths");
                searchPaths.setAccessible(true);

                libraries = NativeLibrary.class.getDeclaredField("libraries");
                libraries.setAccessible(true);
            }

            Map<String, Reference<NativeLibrary>> libs = (Map<String, Reference<NativeLibrary>>) libraries.get(null);
            Map<String, List<String>> paths = (Map<String, List<String>>) searchPaths.get(null);
            libs.remove(VideoLan4J.LIBVLC_NAME);
            libs.remove(VideoLan4J.LIBVLCCORE_NAME);
            paths.remove(VideoLan4J.LIBVLC_NAME);
            paths.remove(VideoLan4J.LIBVLCCORE_NAME);
            return true;
        } catch (Exception e) {
            LOGGER.error(IT, "attempt to fix failed", e);
        }
        return false;
    }
}
