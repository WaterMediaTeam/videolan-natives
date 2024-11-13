package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class libvlc_audio_track_t extends Structure {
    public static class ByValue extends libvlc_audio_track_t implements Structure.ByValue {}

    public int i_channels;
    public int i_rate;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_channels", "i_rate");
    }
}