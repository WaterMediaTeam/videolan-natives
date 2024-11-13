package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_position_changed extends Structure {
    public float new_position;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_position");
    }
}