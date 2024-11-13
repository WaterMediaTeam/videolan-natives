package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_subitem_added extends Structure {
    public libvlc_media_t new_child;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_child");
    }
}