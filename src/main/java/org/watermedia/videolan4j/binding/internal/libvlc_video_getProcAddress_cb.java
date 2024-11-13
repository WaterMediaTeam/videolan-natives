package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_video_getProcAddress_cb extends Callback {

    /**
     * Callback prototype to load opengl functions
     *
     * @param opaque private pointer passed to the @a libvlc_video_set_output_callbacks() [IN]
     * @param fct_name name of the opengl function to load - do not release this string
     * @return a pointer to the named OpenGL function the NULL otherwise
     *
     * @since LibVLC 4.0.0 or later
     */
    Pointer getProcAddress(Pointer opaque, String fct_name);
}