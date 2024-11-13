package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_video_makeCurrent_cb extends Callback {
    /**
     * Callback prototype to set up the OpenGL context for rendering
     *
     * @param opaque private pointer passed to the @a libvlc_video_set_output_callbacks() [IN]
     * @param enter true to set the context as current, false to unset it [IN]
     * @return true on success
     *
     * @since LibVLC 4.0.0 or later
     */
    int makeCurrent(Pointer opaque, int enter);
}