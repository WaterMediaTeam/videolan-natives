package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Union;

public class libvlc_media_track_u extends Union {

    public static class ByReference extends libvlc_media_track_u implements Union.ByReference {}

    public libvlc_audio_track_t audio;
    public libvlc_video_track_t video;
    public libvlc_subtitle_track_t subtitle;
}