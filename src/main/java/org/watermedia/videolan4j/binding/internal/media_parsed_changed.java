package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_parsed_changed extends Structure {
    public int new_status;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_status");
    }
}