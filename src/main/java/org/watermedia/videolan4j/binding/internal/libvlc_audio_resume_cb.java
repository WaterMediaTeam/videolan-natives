package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_resume_cb extends Callback {

    /**
     * Callback prototype for audio resumption (i.e. restart from pause).
     * <p>
     * Note: The resume callback is never called if the audio is not paused.
     *
     * @param data data pointer as passed to libvlc_audio_set_callbacks()
     * @param pts time stamp of the resumption request (should be elapsed already)
     */
    void resume(Pointer data, long pts);
}