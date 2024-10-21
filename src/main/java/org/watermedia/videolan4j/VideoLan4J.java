package org.watermedia.videolan4j;

import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import org.watermedia.videolan4j.binding.internal.libvlc_instance_t;
import org.watermedia.videolan4j.binding.internal.libvlc_media_t;
import org.watermedia.videolan4j.binding.lib.LibC;
import org.watermedia.videolan4j.tools.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.watermedia.videolan4j.binding.lib.LibVlc;

import java.io.File;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Consumer;
import java.util.function.Function;

public class VideoLan4J {
    public static final Logger LOGGER = LogManager.getLogger("VideoLan4J");

    public static final String LIBVLC_NAME = Platform.isWindows() ? "libvlc" : "vlc";
    public static final String LIBVLCCORE_NAME = Platform.isWindows() ? "libvlccore" : "vlccore";
    public static final Version LIBVLC_MIN_VERSION = new Version("3.0.0");
    public static Function<Integer, ByteBuffer> DIRECT_BUFFER_BUILDER = (size) -> (ByteBuffer.allocateDirect(size)).order(ByteOrder.nativeOrder());
    public static Consumer<ByteBuffer> DIRECT_BUFFER_RELEASER = (b) -> {};

    /**
     * Process ID
     * <p>LibC can't get pid on windows, Kernel32 is used instead,
     * other OS fallbacks to LibC</p>
     */
    public static final int PID = Platform.isWindows() ? Kernel32.INSTANCE.GetCurrentProcessId() : LibC.INSTANCE.getpid();

    /**
     * Encodes {@link URI} into s MRL string<br>
     * The {@link File#toString()} method returns the <code>file:///</code> protocol just with one slash instead of three.
     * Method does a special handling for that
     */
    public static libvlc_media_t getMediaInstance(libvlc_instance_t vlc, URI uri) {
        return uri.getScheme().equals("file")
                ? LibVlc.libvlc_media_new_path(vlc, new File(uri.getPath()).toString())
                : LibVlc.libvlc_media_new_location(vlc, uri.toString());
    }

    /**
     * Encodes {@link File} into a MRL string
     */
    public static libvlc_media_t getMediaInstance(libvlc_instance_t vlc, File url) {
        return LibVlc.libvlc_media_new_path(vlc, url.toString());
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
     * @param bufferBuilder builder function, it cannot be null
     */
    public static void setCustomDirectBufferBuilder(Function<Integer, ByteBuffer> bufferBuilder) {
        DIRECT_BUFFER_BUILDER = bufferBuilder;
    }

    /**
     * Crates a direct bytebuffer using the best VLCJ-suggested way
     * Can be replaced using other builder
     *
     * @param size buffer size
     * @see #setCustomDirectBufferBuilder(Function)
     * @return direct ByteBuffer instance
     */
    public static ByteBuffer createDirectByteBuffer(int size) {
        return DIRECT_BUFFER_BUILDER.apply(size);
    }

    /**
     * Replaces the default bytebuffer releaser
     * in favor of better, modern or even more direct implementation
     * @param bufferReleaser
     */
    public static void setCustomDirectBufferReleaser(Consumer<ByteBuffer> bufferReleaser) {
        DIRECT_BUFFER_RELEASER = bufferReleaser;
    }

    /**
     * Deallocates provided bytebuffer ussing the best VLCJ-suggested way
     * Can be replaced using other releaser
     *
     * @param byteBuffer buffer to release, normally only takes care of it address
     */
    public static void releaseDirectByteBuffer(ByteBuffer byteBuffer) {
        DIRECT_BUFFER_RELEASER.accept(byteBuffer);
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

    public static Version getVideoLanVersion() {
        return new Version(LibVlc.libvlc_get_version());
    }
}
