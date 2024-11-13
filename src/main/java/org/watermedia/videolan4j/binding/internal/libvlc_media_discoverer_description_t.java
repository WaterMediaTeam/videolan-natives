package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class libvlc_media_discoverer_description_t extends Structure {
    public static class ByReference extends libvlc_media_discoverer_description_t implements Structure.ByReference {}

    public String psz_name;
    public String psz_longname;
    public int i_cat;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("psz_name", "psz_longname", "i_cat");
    }
}