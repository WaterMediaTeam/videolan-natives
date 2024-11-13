package org.watermedia.videolan4j.binding.internal;

import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class media_player_audio_volume extends Structure {
    /**
     * Volume or gain level. VLC already does (vol * vol), 0.0 means 0% and 2.0 means 200%
     */
    public float volume;

    @Override
    protected List<String> getFieldOrder() {
        return Collections.singletonList("volume");
    }
}