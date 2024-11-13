package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * Encapsulation of a chapter description.
 */
public class libvlc_title_description_t extends Structure {
    public static class ByReference extends libvlc_title_description_t implements Structure.ByReference {}

    /**
     * In milliseconds
     */
    public long i_duration;
    public Pointer psz_name;
    public byte b_menu;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_duration", "psz_name", "b_menu");
    }
}