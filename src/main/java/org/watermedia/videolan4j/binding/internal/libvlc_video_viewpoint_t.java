package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class libvlc_video_viewpoint_t extends Structure {
    public static class ByValue extends libvlc_video_viewpoint_t implements Structure.ByValue {}

    public float f_yaw;           /** view point yaw in degrees */
    public float f_pitch;         /** view point pitch in degrees */
    public float f_roll;          /** view point roll in degrees */
    public float f_field_of_view; /** field of view in degrees (default 80.0f) */

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("f_yaw", "f_pitch", "f_roll", "f_field_of_view");
    }
}