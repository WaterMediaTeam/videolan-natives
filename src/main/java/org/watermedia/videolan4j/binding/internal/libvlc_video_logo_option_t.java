package org.watermedia.videolan4j.binding.internal;

/**
 * Enumeration of logo options.
 */
public enum libvlc_video_logo_option_t {

    libvlc_logo_enable,
    libvlc_logo_file,           /** string argument, "file,d,t;file,d,t;..." */
    libvlc_logo_x,
    libvlc_logo_y,
    libvlc_logo_delay,
    libvlc_logo_repeat,
    libvlc_logo_opacity,
    libvlc_logo_position;

    public int intValue() {
        return this.ordinal();
    }
}