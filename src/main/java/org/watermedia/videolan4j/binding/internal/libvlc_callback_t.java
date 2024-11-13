package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_callback_t extends Callback {

    /**
     * @param event event
     * @param userData user-data pointer
     */
    void callback(libvlc_event_t event, Pointer userData);
}