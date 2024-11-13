package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class libvlc_rectangle_t extends Structure {
    public int top;
    public int left;
    public int bottom;
    public int right;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("top", "left", "bottom", "right");
    }
}