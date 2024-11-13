package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/**
 * Encapsulation of a track description.
 */
public class libvlc_track_description_t extends Structure {
    public static class ByReference extends libvlc_track_description_t implements Structure.ByReference {}

    public int i_id;
    public String psz_name;
    public libvlc_track_description_t.ByReference p_next;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_id", "psz_name", "p_next");
    }
}