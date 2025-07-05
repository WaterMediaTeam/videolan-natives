package org.watermedia.videolan4j.tools;

/**
 * Enum representing various audio formats used in the application.
 * Each format is defined by its channel count, sample rate, and bits per sample.
 * <p>
 * Format is <code>[signed|unsigned]-[bits per sample]-[native|big endian|little endian]</code>.
 * For example, {@link AudioFormat#S16N_STEREO_44 S16N} stands for "signed 16 bits per sample, native endian."
 * </p>
 * To make formats quick to use, all formats has surround variants with 44100Hz, 48000Hz, and 96000Hz sample rates
 * and channel counts in mono, stereo, and surround configurations.
 */
public enum AudioFormat {
    // 8-bit unsigned formats (compatibility)
    U8_MONO_44          ("U8", 1, 44100, 8),
    U8_STEREO_44        ("U8", 2, 44100, 8),
    U8_SURROUND_44      ("U8", 6, 44100, 8),
    U8_SURROUND71_44    ("U8", 8, 44100, 8),

    // 16-bit signed native formats - 44.1kHz
    S16N_MONO_44        ("S16N", 1, 44100, 16),
    S16N_STEREO_44      ("S16N", 2, 44100, 16),
    S16N_SURROUND_44    ("S16N", 6, 44100, 16),
    S16N_SURROUND71_44  ("S16N", 8, 44100, 16),

    // 16-bit signed native formats - 48kHz
    S16N_MONO_48        ("S16N", 1, 48000, 16),
    S16N_STEREO_48      ("S16N", 2, 48000, 16),
    S16N_SURROUND_48    ("S16N", 6, 48000, 16),
    S16N_SURROUND71_48  ("S16N", 8, 48000, 16),

    // 16-bit signed native formats - 96kHz
    S16N_MONO_96        ("S16N", 1, 96000, 16),
    S16N_STEREO_96      ("S16N", 2, 96000, 16),
    S16N_SURROUND_96    ("S16N", 6, 96000, 16),
    S16N_SURROUND71_96  ("S16N", 8, 96000, 16),

    // 32-bit signed native formats - 44.1kHz
    S32N_MONO_44        ("S32N", 1, 44100, 32),
    S32N_STEREO_44      ("S32N", 2, 44100, 32),
    S32N_SURROUND_44    ("S32N", 6, 44100, 32),
    S32N_SURROUND71_44  ("S32N", 8, 44100, 32),

    // 32-bit signed native formats - 48kHz
    S32N_MONO_48        ("S32N", 1, 48000, 32),
    S32N_STEREO_48      ("S32N", 2, 48000, 32),
    S32N_SURROUND_48    ("S32N", 6, 48000, 32),
    S32N_SURROUND71_48  ("S32N", 8, 48000, 32),

    // 32-bit signed native formats - 96kHz
    S32N_MONO_96        ("S32N", 1, 96000, 32),
    S32N_STEREO_96      ("S32N", 2, 96000, 32),
    S32N_SURROUND_96    ("S32N", 6, 96000, 32),
    S32N_SURROUND71_96  ("S32N", 8, 96000, 32),

    // 32-bit float formats - 44.1kHz
    FL32_MONO_44        ("FL32", 1, 44100, 32),
    FL32_STEREO_44      ("FL32", 2, 44100, 32),
    FL32_SURROUND_44    ("FL32", 6, 44100, 32),
    FL32_SURROUND71_44  ("FL32", 8, 44100, 32),

    // 32-bit float formats - 48kHz
    FL32_MONO_48        ("FL32", 1, 48000, 32),
    FL32_STEREO_48      ("FL32", 2, 48000, 32),
    FL32_SURROUND_48    ("FL32", 6, 48000, 32),
    FL32_SURROUND71_48  ("FL32", 8, 48000, 32),

    // 32-bit float formats - 96kHz
    FL32_MONO_96        ("FL32", 1, 96000, 32),
    FL32_STEREO_96      ("FL32", 2, 96000, 32),
    FL32_SURROUND_96    ("FL32", 6, 96000, 32),
    FL32_SURROUND71_96  ("FL32", 8, 96000, 32);

    private final String formatName;
    private final int channelCount;
    private final int sampleRate;
    private final int bitsPerSample;

    /**
     * Number of channels in the audio format.
     * @param channelCount number of audio channels
     * @param sampleRate sample rate in Hz, usually 44100hz, 48000hz, or 96000hz
     * @param bitsPerSample bits per sample, usually 16 or 32 bits
     */
    AudioFormat(String formatName, int channelCount, int sampleRate, int bitsPerSample) {
        this.formatName = formatName;
        this.channelCount = channelCount;
        this.sampleRate = sampleRate;
        this.bitsPerSample = bitsPerSample;
    }

    public String getFormatName() {
        return formatName;
    }

    public int getChannelCount() {
        return channelCount;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getBitsPerSample() {
        return bitsPerSample;
    }

    public int calculateBufferSize(int sampleCount) {
        return sampleCount * this.channelCount * (this.bitsPerSample / 8);
    }
}
