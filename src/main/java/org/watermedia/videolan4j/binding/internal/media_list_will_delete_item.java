package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class media_list_will_delete_item extends Structure {
    public libvlc_media_t item;
    public int index;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("item", "index");
    }
}