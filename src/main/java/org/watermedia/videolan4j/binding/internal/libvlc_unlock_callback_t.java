package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_unlock_callback_t extends Callback {

    /**
     * @param opaque application-specific pointer
     * @param picture picture pointer
     * @param plane plane pointer
     */
    void unlock(Pointer opaque, Pointer picture, Pointer plane);
}