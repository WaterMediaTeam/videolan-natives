package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_video_update_output_cb extends Callback {
    /**
     * Callback prototype called on video size changes
     *
     * @param opaque private pointer passed to the @a libvlc_video_set_output_callbacks() [IN]
     * @param width video width in pixel [IN]
     * @param height video height in pixel [IN]
     *
     * @since LibVLC 4.0.0 or later
     */
    void updateOutput(Pointer opaque, int width, int height);
}