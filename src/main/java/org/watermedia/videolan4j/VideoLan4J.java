package org.watermedia.videolan4j;

import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import org.watermedia.videolan4j.binding.internal.libvlc_instance_t;
import org.watermedia.videolan4j.binding.internal.libvlc_media_player_t;
import org.watermedia.videolan4j.binding.internal.libvlc_media_t;
import org.watermedia.videolan4j.binding.lib.LibVlcEssential;
import org.watermedia.videolan4j.discovery.NativeDiscovery;
import org.watermedia.videolan4j.tools.Buffers;
import org.watermedia.videolan4j.tools.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.watermedia.videolan4j.binding.lib.LibVlc;

import java.io.File;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class VideoLan4J {
    public static final Logger LOGGER = LogManager.getLogger("VideoLan4J");

    public static final String LIBVLC_NAME = Platform.isWindows() ? "libvlc" : "vlc";
    public static final String LIBVLCCORE_NAME = Platform.isWindows() ? "libvlccore" : "vlccore";
    public static final String LIBVLC_PLUGIN_ENV_NAME = "VLC_PLUGIN_PATH";

    public static final Version LIBVLC_MIN_VERSION = new Version("3.0.0");
    public static final Version LIBVLC_MAX_VERSION = new Version("3.1.0");
    public static final int LIBVLC_BUFFER_ALIGNMENT = 32;

    public static final String VLC4J_USER_DISCOVERY_PATH = System.getProperty("vlc4j.userDiscoveryPath");

    private static libvlc_instance_t DEFAULT_INSTANCE;

    public static boolean isDiscovered() { return NativeDiscovery.discovered() && DEFAULT_INSTANCE != null; }
    public static String discoveryPath() { return NativeDiscovery.discoveryPath(); }

    public static libvlc_instance_t getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }


    public static synchronized boolean load(String... defaultArgs) {
        boolean loaded = NativeDiscovery.start();

        if (loaded && DEFAULT_INSTANCE == null) {
            DEFAULT_INSTANCE = createInstance(defaultArgs);
            if (DEFAULT_INSTANCE == null) {
                LOGGER.error("Failed to create default libvlc instance");
                loaded = false;
            }
        }

        return loaded;
    }

    /**
     * Create a new libvlc instance with the given arguments.
     * <p>
     * The arguments are passed to the libvlc instance, which can be used to configure it.
     * </p>
     *
     * @param args command-line-type arguments
     * @return a new libvlc instance
     */
    public static libvlc_instance_t createInstance(String... args) {
        return LibVlcEssential.libvlc_new(0, new StringArray(args)); // TODO: check if argc must be args length (array.length)
    }

    /**
     * Release the given libvlc instance.
     */
    public static void releaseInstance(libvlc_instance_t instance) {
        LibVlcEssential.libvlc_release(instance);
    }

    /**
     * Create a new media player instance for the given libvlc instance.
     *
     * @param instance the libvlc instance to create the media player for
     * @return a new media player instance
     */
    public static libvlc_media_player_t createMediaPlayer(libvlc_instance_t instance) {
        return LibVlc.libvlc_media_player_new(instance);
    }

    /**
     * Create a new media player instance for the default internal libvlc instance
     *
     * @return a new media player instance
     */
    public static libvlc_media_player_t createMediaPlayer() {
        return LibVlc.libvlc_media_player_new(DEFAULT_INSTANCE);
    }

    /**
     * Release the given media player instance.
     * <p>
     * This method decrements the reference count of the media player and destroys it if the count reaches zero.
     *
     * @param player the media player instance to release
     */
    public static void releaseMediaPlayer(libvlc_media_player_t player) {
        if (player != null) {
            LibVlc.libvlc_media_player_release(player);
        }
    }

    /**
     * Encodes {@link URI} into a MRL string<br>
     * The {@link File#toString()} method returns the <code>file:///</code> protocol just with one slash instead of three.
     * Method does a special handling for that
     */
    public static libvlc_media_t createMediaInstance(libvlc_instance_t vlc, URI uri) {
        return uri.getScheme().equals("file")
                ? LibVlc.libvlc_media_new_path(vlc, new File(uri.getPath()).toString())
                : LibVlc.libvlc_media_new_location(vlc, uri.toString());
    }

    /**
     * Encodes {@link File} into a MRL string
     */
    public static libvlc_media_t createMediaInstance(libvlc_instance_t vlc, File url) {
        return LibVlc.libvlc_media_new_path(vlc, url.toString());
    }

    /**
     * Encodes {@link URI} into a MRL string using the default internal libvlc instance<br>
     * The {@link File#toString()} method returns the <code>file:///</code> protocol just with one slash instead of three.
     * Method does a special handling for that
     */
    public static libvlc_media_t createMediaInstance(URI uri) {
        return uri.getScheme().equals("file")
                ? LibVlc.libvlc_media_new_path(DEFAULT_INSTANCE, new File(uri.getPath()).toString())
                : LibVlc.libvlc_media_new_location(DEFAULT_INSTANCE, uri.toString());
    }

    /**
     * Encodes {@link File} into a MRL string using the default internal libvlc instance
     */
    public static libvlc_media_t createMediaInstance(File url) {
        return LibVlc.libvlc_media_new_path(DEFAULT_INSTANCE, url.toString());
    }

    /**
     * Releases the given media instance.
     * <p>
     * This method decrements the reference count of the media and destroys it if the count reaches zero.
     *
     * @param media the media instance to release
     */
    public static void releaseMediaInstance(libvlc_media_t media) {
        if (media != null) {
            LibVlc.libvlc_media_release(media);
        }
    }

    /**
     * Validate if the current thread has any context ClassLoader
     * Java usually fallbacks to system ClassLoader but modded environments (forge)
     * fall in an exception assuming ClassLoader is never null, crashing the native threads
     * @param classLoader reference of the ClassLoader used by to load the callback Class
     */
    public static void checkClassLoader(ClassLoader classLoader) {
        Thread t = Thread.currentThread();
        if (t.getContextClassLoader() == null) t.setContextClassLoader(classLoader);
    }

    /**
     * Replaces the default direct bytebuffer builder
     * in favor of better, modern or even more direct implementations
     *
     * @param bufferAllocator function implementation
     */
    public static void setBufferAllocator(BiFunction<Integer, Integer, ByteBuffer> bufferAllocator) {
        Buffers.setBufferAllocator(bufferAllocator);
    }

    /**
     * Replaces the default bytebuffer releaser
     * in favor of better, modern or even more direct implementation
     * @param bufferDeallocator consumer implementation
     */
    public static void setBufferDeallocator(Consumer<ByteBuffer> bufferDeallocator) {
        Buffers.setBufferDeallocator(bufferDeallocator);
    }

    /**
     * Get a String from a native string pointer, freeing the native string pointer when done.
     * <p>
     * If the native string pointer is not freed then a native memory leak will occur.
     * <p>
     * Use this method if the native string type is "char*", i.e. lacking the "const" modifier.
     *
     * @param pointer pointer to native string, may be <code>null</code>
     * @return string, or <code>null</code> if the pointer was <code>null</code>
     */
    public static String copyAndFreeNativeString(Pointer pointer) {
        try {
            return copyNativeString(pointer);
        } finally {
            freeNativeString(pointer);
        }
    }

    /**
     * frees the native string pointer
     * <p>
     * If the native string pointer is not freed then a native memory leak will occur.
     * <p>
     * Use this method if the native string type is "char*", i.e. lacking the "const" modifier.
     * @param pointer pointer to native string, may be <code>null</code>
     */
    public static void freeNativeString(Pointer pointer) {
        if (pointer != null) {
            LibVlc.libvlc_free(pointer);
        }
    }

    /**
     * Copy a String from a native string pointer, without freeing the native pointer.
     * <p>
     * Use this method if the native string type is "const char*".
     *
     * @param pointer pointer to native string, may be <code>null</code>
     * @return string, or <code>null</code> if the pointer was <code>null</code>
     */
    public static String copyNativeString(Pointer pointer) {
        return (pointer != null) ? pointer.getString(0) : null;
    }

    public static Version getLibVersion() {
        return new Version(LibVlcEssential.libvlc_get_version());
    }

    public static boolean isLibSupported() {
        return getLibVersion().inRange(LIBVLC_MIN_VERSION, LIBVLC_MAX_VERSION);
    }
}