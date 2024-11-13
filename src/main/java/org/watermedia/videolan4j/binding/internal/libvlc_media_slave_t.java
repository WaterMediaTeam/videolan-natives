package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class libvlc_media_slave_t extends Structure {

    public libvlc_media_slave_t() {}
    public libvlc_media_slave_t(Pointer value) {
        super(value);
        this.read();
    }

    public Pointer psz_uri;
    public int i_type;
    public int i_priority;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("psz_uri", "i_type", "i_priority");
    }
}
