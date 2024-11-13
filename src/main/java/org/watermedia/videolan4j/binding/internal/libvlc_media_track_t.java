package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

public class libvlc_media_track_t extends Structure {
    public int i_codec;
    public int i_original_fourcc;
    public int i_id;
    public int i_type;

    public int i_profile;
    public int i_level;

    public libvlc_media_track_u.ByReference u;

    public int i_bitrate;
    public Pointer psz_language;
    public Pointer psz_description;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_codec", "i_original_fourcc", "i_id", "i_type", "i_profile", "i_level", "u", "i_bitrate", "psz_language", "psz_description");
    }
}