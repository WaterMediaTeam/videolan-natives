package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_video_swap_cb extends Callback {
    /**
     * Callback prototype called after performing drawing calls.
     *
     * @param opaque private pointer passed to the @a libvlc_video_set_output_callbacks() [IN]
     *
     * @since LibVLC 4.0.0 or later
     */
    void swap(Pointer opaque);
}