package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class media_player_chapter_changed extends Structure {
    public int new_chapter;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_chapter");
    }
}