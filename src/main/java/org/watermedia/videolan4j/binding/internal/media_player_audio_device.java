package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_audio_device extends Structure {
    public String device;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("device");
    }
}