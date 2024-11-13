package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class libvlc_video_track_t extends Structure {
    public static class ByValue extends libvlc_video_track_t implements Structure.ByValue {}

    public int i_height;
    public int i_width;

    public int i_sar_num;
    public int i_sar_den;
    public int i_frame_rate_num;
    public int i_frame_rate_den;

    public int i_orientation;
    public int i_projection;
    public libvlc_video_viewpoint_t.ByValue pose;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("i_height", "i_width", "i_sar_num", "i_sar_den", "i_frame_rate_num", "i_frame_rate_den", "i_orientation", "i_projection", "pose");
    }
}