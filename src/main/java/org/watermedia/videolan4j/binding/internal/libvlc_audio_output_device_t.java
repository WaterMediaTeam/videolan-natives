package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Description for audio output device.
 */
public class libvlc_audio_output_device_t extends Structure {
    public static class ByReference extends libvlc_audio_output_device_t implements Structure.ByReference {}

    /**
     * Next entry in list
     */
    public libvlc_audio_output_device_t.ByReference p_next;
    /**
     * Device identifier string
     */
    public Pointer                                  psz_device;
    /**
     * User-friendly device description
     */
    public Pointer                                  psz_description;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("p_next", "psz_device", "psz_description");
    }
}
