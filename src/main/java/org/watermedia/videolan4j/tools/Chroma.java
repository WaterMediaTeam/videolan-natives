package org.watermedia.videolan4j.tools;

// https://wiki.videolan.org/Chroma/
public enum Chroma {
    /**
     * RGBA is a 32-bit RGBA format in a single plane.
     */
    RGBA {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width * 4 };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height };
        }
    },

    /**
     * RV32 is a 24-bit BGR format with 8-bit of padding (no alpha) in a single plane.
     */
    RV32 {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width * 4 };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height };
        }
    },


    /**
     * RV24 is a 24-bit RGB format in a single plane.
     */
    Rv24 {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width * 3 };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height };
        }
    },

    /**
     * YUYV is a 16-bit YUV format with 4:2:2 subsampling in a single plane.
     */
    YUYV {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width * 2 };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height };
        }
    },

    /**
     * UYVY is a 16-bit YUV format with 4:2:2 subsampling in a single plane.
     */
    UYVY {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width * 2 };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height };
        }
    },

    /**
     * GRAW is a 16-bit grayscale format in a single plane.
     */
    GRAW {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height };
        }
    },

    NV12 {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width, width };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height, height / 2 };
        }
    },

    I420 {
        @Override
        public int[] getPitches(int width) {
            return new int[] { width, width / 2, width / 2 };
        }

        @Override
        public int[] getLines(int height) {
            return new int[] { height, height / 2, height / 2 };
        }
    };

    public abstract int[] getPitches(int width);

    public abstract int[] getLines(int height);

    public String canonical() {
        return this.name().toLowerCase();
    }
}