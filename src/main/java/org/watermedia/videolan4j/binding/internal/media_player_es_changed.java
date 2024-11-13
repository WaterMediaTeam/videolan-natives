package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_es_changed extends Structure {
    public int i_type;
    public int i_id;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_type", "i_id");
    }
}