package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_snapshot_taken extends Structure {
    public String filename;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("filename");
    }
}