package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_subitemtree_added extends Structure {
    public libvlc_media_t item;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("item");
    }
}