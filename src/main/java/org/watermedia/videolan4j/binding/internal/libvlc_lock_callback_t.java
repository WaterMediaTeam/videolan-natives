package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Callback prototype to allocate and lock a picture buffer.
 */
public interface libvlc_lock_callback_t extends Callback {

    /**
     * Callback prototype to allocate and lock a picture buffer.
     * <p>
     * Whenever a new video frame needs to be decoded, the lock callback is invoked. Depending on
     * the video chroma, one or three pixel planes of adequate dimensions must be returned via the
     * second parameter. Those planes must be aligned on 32-bytes boundaries.
     *
     * @param opaque private pointer as passed to libvlc_video_set_callbacks() [IN]
     * @param planes start address of the pixel planes (LibVLC allocates the array of void pointers, this callback must initialize the array) [OUT]
     * @return a private pointer for the display and unlock callbacks to identify the picture buffers
     */
    Pointer lock(Pointer opaque, PointerByReference planes);
}