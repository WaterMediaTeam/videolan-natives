package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_meta_changed extends Structure {
    /**
     * Type of meta.
     * <p>
     * <strong>This value may be unreliable, for example when parsing media some types are reported
     * multiple times and some types are not reported at all.</strong>
     */
    public int meta_type;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("meta_type");
    }
}