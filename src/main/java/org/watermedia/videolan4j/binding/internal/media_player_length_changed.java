package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_length_changed extends Structure {
    public long new_length;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_length");
    }
}