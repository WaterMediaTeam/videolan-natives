package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Encapsulation of a chapter description.
 */
public class libvlc_chapter_description_t extends Structure {
public static class ByReference extends libvlc_chapter_description_t implements Structure.ByReference {}

    /**
     * In Milliseconds
     */
    public long i_time_offset;
    /**
     * In Milliseconds
     */
    public long i_duration;
    public Pointer psz_name;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_time_offset", "i_duration", "psz_name");
    }
}