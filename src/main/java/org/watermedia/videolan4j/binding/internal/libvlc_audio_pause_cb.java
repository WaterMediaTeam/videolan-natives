package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_pause_cb extends Callback {

    /**
     * Callback prototype for audio pause.
     * <p>
     * Note: The pause callback is never called if the audio is already paused.
     *
     * @param data data pointer as passed to libvlc_audio_set_callbacks()
     * @param pts time stamp of the pause request (should be elapsed already)
     */
    void pause(Pointer data, long pts);
}