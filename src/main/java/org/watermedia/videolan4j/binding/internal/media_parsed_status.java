package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Collections;
import java.util.List;

public class media_parsed_status extends Structure {
    public int new_status;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("new_status");
    }
}