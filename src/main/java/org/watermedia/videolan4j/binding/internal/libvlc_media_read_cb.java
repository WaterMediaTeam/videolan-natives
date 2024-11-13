package org.watermedia.videolan4j.binding.internal;

import org.watermedia.videolan4j.binding.lib.types.size_t;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * Native read media callback.
 */
public interface libvlc_media_read_cb extends Callback {

    /**
     * Callback prototype to read data from a custom bitstream input media.
     * <p>
     * <em>If no data is immediately available, then the callback should sleep.</em>
     * <p>
     * <strong>The application is responsible for avoiding deadlock situations.
     * In particular, the callback should return an error if playback is stopped;
     * if it does not return, then libvlc_media_player_stop() will never return.</strong>
     *
     * @param opaque private pointer as set by the @ref libvlc_media_open_cb callback
     * @param buf start address of the buffer to read data into
     * @param len bytes length of the buffer
     *
     * @return strictly positive number of bytes read, 0 on end-of-stream, or -1 on non-recoverable error
     */
    size_t read(Pointer opaque, Pointer buf, size_t len);
}