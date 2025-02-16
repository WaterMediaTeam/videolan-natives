package org.watermedia.videolan4j;

import java.util.function.BiFunction;

// https://wiki.videolan.org/Chroma/
public enum BufferFormat {
    RGBA((w, h) -> new int[] { w * 4            }, (w, h) -> new int[] { h                  }),
    Rv24((w, h) -> new int[] { w * 3            }, (w, h) -> new int[] { h                  }),
    YUYV((w, h) -> new int[] { w * 2            }, (w, h) -> new int[] { h                  }),
    UYVY((w, h) -> new int[] { w * 2            }, (w, h) -> new int[] { h                  }),
    GRAW((w, h) -> new int[] { w                }, (w, h) -> new int[] { h                  }),
    NV12((w, h) -> new int[] { w, w             }, (w, h) -> new int[] { h, h / 2           }),
    I420((w, h) -> new int[] { w, w / 2, w / 2  }, (w, h) -> new int[] { h, h / 2, h / 2    });

    private final BiFunction<Integer, Integer, int[]> pitches;
    private final BiFunction<Integer, Integer, int[]> lines;
    BufferFormat(BiFunction<Integer, Integer, int[]> pitches, BiFunction<Integer, Integer, int[]> lines) {
        this.pitches = pitches;
        this.lines = lines;
    }

    public int[] getPitches(int width, int height) {
        return this.pitches.apply(width, height);
    }

    public int[] getLines(int width, int height) {
        return this.lines.apply(width, height);
    }

    public String getChroma() {
        return this.name();
    }
}