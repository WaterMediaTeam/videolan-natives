package uk.co.caprica.vlcj;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uk.co.caprica.vlcj.binding.internal.libvlc_instance_t;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.binding.lib.LibC;
import uk.co.caprica.vlcj.binding.lib.LibVlc;

import java.io.File;
import java.net.URL;

public class VideoLan4J {
    public static final Logger LOGGER = LogManager.getLogger("VideoLan4J");

    public static final String LIBVLC_NAME = Platform.isWindows() ? "libvlc" : "vlc";
    public static final String LIBVLCCORE_NAME = Platform.isWindows() ? "libvlccore" : "vlccore";

    /**
     * Process ID
     * <p>LibC can't get pid on windows, Kernel32 is used instead,
     * other OS fallbacks to LibC</p>
     */
    public static final int PID = Platform.isWindows() ? Kernel32.INSTANCE.GetCurrentProcessId() : LibC.INSTANCE.getpid();

    /**
     * Encodes {@link URL} into s MRL string<br>
     * The {@link URL#toString()} method returns the <code>File</code> protocol with one slashes instead of three.
     * Method does a special handling for that
     */
    public static libvlc_media_t getMediaInstance(libvlc_instance_t vlc, URL url) {
        String mrl = url.toString();
        if (mrl.startsWith("file:/") && !mrl.startsWith("file:///")) mrl = mrl.replace("file:/", "file:///");
        return LibVlc.libvlc_media_new_location(vlc, mrl);
    }

    /**
     * Encodes {@link File} into a MRL string
     */
    public static libvlc_media_t getMediaInstance(libvlc_instance_t vlc, File url) {
        return LibVlc.libvlc_media_new_location(vlc, url.toString());
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
}
