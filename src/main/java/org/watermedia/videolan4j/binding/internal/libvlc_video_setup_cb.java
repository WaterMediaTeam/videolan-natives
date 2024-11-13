package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_video_setup_cb extends Callback {
    /**
     * Callback prototype called to initialize user data.
     *
     * @param opaque private pointer passed to the @a libvlc_video_set_output_callbacks() [IN]
     * @return true on success
     *
     * @since LibVLC 4.0.0 or later
     */
    int setup(Pointer opaque);
}