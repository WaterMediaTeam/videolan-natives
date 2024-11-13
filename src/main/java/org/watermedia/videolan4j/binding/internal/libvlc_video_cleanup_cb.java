package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_video_cleanup_cb extends Callback {
    /**
     * @param opaque application-specific pointer
     */
    void cleanup(Pointer opaque);
}