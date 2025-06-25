package org.watermedia.videolan4j.tools;

import org.watermedia.videolan4j.binding.internal.libvlc_media_stats_t;

public class MediaStats {
    private final libvlc_media_stats_t rawStats;

    public MediaStats(libvlc_media_stats_t stats) {
        this.rawStats = stats;
    }

    public int inputBytesRead() {
        return this.rawStats.i_read_bytes;
    }
    public float inputBitrate() {
        return this.rawStats.f_input_bitrate;
    }
    public int demuxBytesRead() {
        return this.rawStats.i_demux_read_bytes;
    }
    public float demuxBitrate() {
        return this.rawStats.f_demux_bitrate;
    }
    public int demuxCorrupted() {
        return this.rawStats.i_demux_corrupted;
    }
    public int demuxDiscontinuity() {
        return this.rawStats.i_demux_discontinuity;
    }
    public int decodedVideo() {
        return this.rawStats.i_decoded_video;
    }
    public int decodedAudio() {
        return this.rawStats.i_decoded_audio;
    }
    public int picturesDisplayed() {
        return this.rawStats.i_displayed_pictures;
    }
    public int picturesLost() {
        return this.rawStats.i_lost_pictures;
    }
    public int audioBuffersPlayed() {
        return this.rawStats.i_played_abuffers;
    }
    public int audioBuffersLost() {
        return this.rawStats.i_lost_abuffers;
    }
    public int sentPackets() {
        return this.rawStats.i_sent_packets;
    }
    public float sendBitrate() {
        return this.rawStats.f_send_bitrate;
    }
}
