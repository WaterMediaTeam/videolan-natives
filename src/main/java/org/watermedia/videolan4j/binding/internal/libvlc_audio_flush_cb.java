package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_audio_flush_cb extends Callback {

    /**
     * Callback prototype for audio buffer flush.
     * <p>
     * (i.e. discard all pending buffers and stop playback as soon as possible).
     *
     * @param data data pointer as passed to libvlc_audio_set_callbacks()
     * @param pts presentation time stamp
     */
    void flush(Pointer data, long pts);
}