package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class libvlc_audio_output_t extends Structure {
    public static class ByReference extends libvlc_audio_output_t implements Structure.ByReference {}

    public Pointer psz_name;
    public Pointer psz_description;
    public libvlc_audio_output_t.ByReference p_next;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("psz_name", "psz_description", "p_next");
    }
}