package org.watermedia.videolan4j.tools;

// https://wiki.videolan.org/Chroma/
public enum Chroma {
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

    public String getChroma() {
        return this.name();
    }
}