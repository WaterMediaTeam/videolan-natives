package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class vlm_media_event extends Structure {
    public String psz_media_name;
    public String psz_instance_name;

    @Override
    public List<String> getFieldOrder() {
        return Arrays.asList("psz_media_name", "psz_instance_name");
    }
}