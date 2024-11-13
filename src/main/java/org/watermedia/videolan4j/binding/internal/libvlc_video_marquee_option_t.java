package org.watermedia.videolan4j.binding.internal;

/**
 * Enumeration of marquee options.
 */
public enum libvlc_video_marquee_option_t {

    libvlc_marquee_Enable,
    /**
     * String argument
     */
    libvlc_marquee_Text,
    libvlc_marquee_Color,
    libvlc_marquee_Opacity,
    libvlc_marquee_Position,
    libvlc_marquee_Refresh,
    libvlc_marquee_Size,
    libvlc_marquee_Timeout,
    libvlc_marquee_X,
    libvlc_marquee_Y;

    private static final libvlc_video_marquee_option_t[] VALUES = values();

    public static libvlc_video_marquee_option_t option(int intValue) {
        if (intValue > VALUES.length - 1)
            throw new UnsupportedOperationException("Navigate mode '" + intValue + "' not implemented");
        if (intValue < 0)
            throw new IllegalArgumentException("Navigate mode must be zero or positive");

        return VALUES[intValue];
    }

    public int intValue() {
        return this.ordinal();
    }
}