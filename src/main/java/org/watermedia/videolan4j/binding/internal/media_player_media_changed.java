package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_media_changed extends Structure {
    public libvlc_media_t md;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("md");
    }
}