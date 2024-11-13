package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public class renderer_discoverer_item_added extends Structure {
    public libvlc_renderer_item_t item;

    @Override
    public List<String> getFieldOrder() {
        return Collections.singletonList("item");
    }
}