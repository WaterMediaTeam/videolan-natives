package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * Native close media callback.
 */
public interface libvlc_media_close_cb extends Callback {

    /**
     * Callback prototype to close a custom bitstream input media.
     *
     * @param opaque private pointer as set by the @ref libvlc_media_open_cb callback
     */
    void close(Pointer opaque);
}