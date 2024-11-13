package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_drain_cb extends Callback {

    /**
     * Callback prototype for audio buffer drain.
     * <p>
     * (i.e. wait for pending buffers to be played).
     *
     * @param data data pointer as passed to libvlc_audio_set_callbacks()
     */
    void drain(Pointer data);
}