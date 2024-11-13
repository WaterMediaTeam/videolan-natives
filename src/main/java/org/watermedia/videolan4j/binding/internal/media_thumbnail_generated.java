package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public class media_thumbnail_generated extends Structure {
    public libvlc_picture_t p_thumbnail;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("p_thumbnail");
    }
}