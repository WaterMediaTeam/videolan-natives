package org.watermedia.videolan4j.binding.lib;

import com.sun.jna.Native;
import com.sun.jna.StringArray;
import org.watermedia.videolan4j.VideoLan4J;
import org.watermedia.videolan4j.binding.internal.libvlc_instance_t;

/**
 * JNA interface for the libvlc native library
 * <p>Purpose of this interface is to access almost all VLC versions before try to bind the main interface</p>
 */
public final class LibVlcEssential {
    static {
        Native.register(VideoLan4J.LIBVLC_NAME);
    }

    private LibVlcEssential() {
    }

    /**
     * Retrieve libvlc version. Example: "1.1.0-git The Luggage"
     *
     * @return a string containing the libvlc version
     */
    public static native String libvlc_get_version();

    /**
     * Retrieve libvlc compiler version. Example: "gcc version 4.2.3 (Ubuntu 4.2.3-2ubuntu6)"
     *
     * @return a string containing the libvlc compiler version
     */
    public static native String libvlc_get_compiler();

    /**
     * Retrieve libvlc changeset. Example: "aa9bce0bc4"
     *
     * @return a string containing the libvlc changeset
     */
    public static native String libvlc_get_changeset();

    /**
     * Create and initialize a libvlc instance.
     *
     * @param argc the number of arguments
     * @param argv command-line-type arguments
     * @return the libvlc instance or NULL in case of error
     */
    public static native libvlc_instance_t libvlc_new(int argc, StringArray argv);

    /**
     * Decrement the reference count of a libvlc instance, and destroy it if it reaches zero.
     *
     * @param p_instance the instance to destroy
     */
    public static native void libvlc_release(libvlc_instance_t p_instance);
}