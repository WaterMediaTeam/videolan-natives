package org.watermedia.videolan4j.binding.internal;

/**
 * Enumeration of video adjustment options.
 */
public enum libvlc_video_adjust_option_t {

    libvlc_adjust_Enable,
    /**
     * Float type
     */
    libvlc_adjust_Contrast,
    /**
     * Float type
     */
    libvlc_adjust_Brightness,
    /**
     * Integer type
     */
    libvlc_adjust_Hue,
    /**
     * Float type
     */
    libvlc_adjust_Saturation,
    /**
     * Float type
     */
    libvlc_adjust_Gamma;

    public int intValue() {
        return this.ordinal();
    }
}