package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_buffering extends Structure {
    /**
     * A percentage complete indicator, ranging from 0.0 to 100.0.
     */
    public float new_cache;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_cache");
    }
}