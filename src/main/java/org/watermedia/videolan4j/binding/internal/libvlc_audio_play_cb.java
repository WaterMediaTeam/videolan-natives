package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_play_cb extends Callback {

    /**
     * Callback prototype for audio playback.
     *
     * @param data data pointer as passed to libvlc_audio_set_callbacks()
     * @param samples pointer to the first audio sample to play back
     * @param count number of audio samples to play back
     * @param pts expected play time stamp
     */
    void play(Pointer data, Pointer samples, int count, long pts);
}