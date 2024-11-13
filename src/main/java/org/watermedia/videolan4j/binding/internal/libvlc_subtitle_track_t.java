package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class libvlc_subtitle_track_t extends Structure {
    public static class ByValue extends libvlc_subtitle_track_t implements Structure.ByValue {}

    public Pointer psz_encoding;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("psz_encoding");
    }
}