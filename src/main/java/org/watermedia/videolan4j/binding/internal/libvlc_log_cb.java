package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

/**
 * Specification for a callback that handles native log messages.
 */
public interface libvlc_log_cb extends Callback {

    /**
     * Callback prototype for LibVLC log message handler.
     * <p>
     * <em>Log message handlers <b>must</b> be thread-safe.</em>
     *
     * @param data data pointer as given to libvlc_log_set()
     * @param level message level
     * @param ctx message context (meta-informations about the message)
     * @param format printf() format string (as defined by ISO C11)
     * @param args variable argument list for the format
     */
    void log(Pointer data, int level, libvlc_log_t ctx, String format, Pointer args);
}