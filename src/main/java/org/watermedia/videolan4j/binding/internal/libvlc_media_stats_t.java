package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sun.jna.Structure;

public class libvlc_media_stats_t extends Structure {
    /* Input */
    public int         i_read_bytes;
    public float       f_input_bitrate;

    /* Demux */
    public int         i_demux_read_bytes;
    public float       f_demux_bitrate;
    public int         i_demux_corrupted;
    public int         i_demux_discontinuity;

    /* Decoders */
    public int         i_decoded_video;
    public int         i_decoded_audio;

    /* Video Output */
    public int         i_displayed_pictures;
    public int         i_lost_pictures;

    /* Audio output */
    public int         i_played_abuffers;
    public int         i_lost_abuffers;

    /* Stream output */
    public int         i_sent_packets;
    public int         i_sent_bytes;
    public float       f_send_bitrate;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(
                "i_read_bytes",
                "f_input_bitrate",
                "i_demux_read_bytes",
                "f_demux_bitrate",
                "i_demux_corrupted",
                "i_demux_discontinuity",
                "i_decoded_video",
                "i_decoded_audio",
                "i_displayed_pictures",
                "i_lost_pictures",
                "i_played_abuffers",
                "i_lost_abuffers",
                "i_sent_packets",
                "i_sent_bytes",
                "f_send_bitrate"
        );
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' +
                "i_read_bytes=" + i_read_bytes + ',' +
                "f_input_bitrate=" + f_input_bitrate + ',' +
                "i_demux_read_bytes=" + i_demux_read_bytes + ',' +
                "f_demux_bitrate=" + f_demux_bitrate + ',' +
                "i_demux_corrupted=" + i_demux_corrupted + ',' +
                "i_demux_discontinuity=" + i_demux_discontinuity + ',' +
                "i_decoded_video=" + i_decoded_video + ',' +
                "i_decoded_audio=" + i_decoded_audio + ',' +
                "i_displayed_pictures=" + i_displayed_pictures + ',' +
                "i_lost_pictures=" + i_lost_pictures + ',' +
                "i_played_abuffers=" + i_played_abuffers + ',' +
                "i_lost_abuffers=" + i_lost_abuffers + ',' +
                "i_sent_packets=" + i_sent_packets + ',' +
                "i_sent_bytes=" + i_sent_bytes + ',' +
                "f_send_bitrate=" + f_send_bitrate + ']';
    }
}