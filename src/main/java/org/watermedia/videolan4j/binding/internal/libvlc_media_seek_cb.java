package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * Native seek media callback.
 */
public interface libvlc_media_seek_cb extends Callback {

    /**
     * Callback prototype to seek a custom bitstream input media.
     *
     * @param opaque private pointer as set by the @ref libvlc_media_open_cb callback
     * @param offset absolute byte offset to seek to
     * @return 0 on success, -1 on error.
     */
    int seek(Pointer opaque, long offset);
}