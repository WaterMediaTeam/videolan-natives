package org.watermedia.videolan4j.binding.internal;

/**
 * Enumeration of DVD navigation modes.
 */
public enum libvlc_navigate_mode_e {
    libvlc_navigate_activate,
    libvlc_navigate_up,
    libvlc_navigate_down,
    libvlc_navigate_left,
    libvlc_navigate_right,
    libvlc_navigate_popup;

    private static final libvlc_navigate_mode_e[] VALUES = values();

    public static libvlc_navigate_mode_e event(int intValue) {
        if (intValue > VALUES.length - 1)
            throw new UnsupportedOperationException("Navigate mode '" + intValue + "' not implemented");
        if (intValue < 0)
            throw new IllegalArgumentException("Navigate mode must be zero or positive");

        return VALUES[intValue];
    }

    @Deprecated
    public int intValue() {
        return this.ordinal();
    }
}