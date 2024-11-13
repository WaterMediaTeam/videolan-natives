package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * Callback prototype to setup the audio playback.
 */
public interface libvlc_audio_setup_cb extends Callback {

    /**
     * Callback prototype to setup the audio playback.
     * <p>
     * This is called when the media player needs to create a new audio output.
     *
     * @param data pointer to the data pointer passed to libvlc_audio_set_callbacks()
     * @param format 4 bytes sample format
     * @param rate sample rate
     * @param channels channels count
     * @return 0 on success, anything else to skip audio playback
     */
    int setup(PointerByReference data, String format, IntByReference rate, IntByReference channels);
}