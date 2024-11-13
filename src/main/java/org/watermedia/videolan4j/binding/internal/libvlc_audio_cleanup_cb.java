package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_cleanup_cb extends Callback {

    /**
     * Callback prototype for audio playback cleanup.
     *
     * @param data opaque data pointer as passed to libvlc_audio_set_callbacks()
     */
    void cleanup(Pointer data);
}