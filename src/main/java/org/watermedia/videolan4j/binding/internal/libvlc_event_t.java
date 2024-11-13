package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class libvlc_event_t extends Structure {
    /**
     * @see libvlc_event_e
     */
    public int type;
    public Pointer obj;
    public libvlc_event_u u;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("type", "obj", "u");
    }
}