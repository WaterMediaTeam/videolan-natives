package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class libvlc_rd_description_t extends Structure {
    public static class ByReference extends libvlc_rd_description_t implements Structure.ByReference {}

    public String psz_name;
    public String psz_longname;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("psz_name", "psz_longname");
    }
}