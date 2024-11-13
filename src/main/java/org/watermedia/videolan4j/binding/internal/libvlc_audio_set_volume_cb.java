package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_set_volume_cb extends Callback {

    /**
     * Callback prototype for audio volume change.
     *
     * @param data data pointer as passed to libvlc_audio_set_callbacks()
     * @param volume linear volume (1 = nominal, 0 = mute)
     * @param mute muted flag
     */
    void setVolume(Pointer data, float volume, int mute);
}