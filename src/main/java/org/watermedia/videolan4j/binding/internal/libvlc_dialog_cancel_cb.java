package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_dialog_cancel_cb extends Callback {

    /**
     * Called when a displayed dialog needs to be cancelled
     * <p>
     * The implementation must call libvlc_dialog_dismiss() to really release
     * the dialog.
     * </p>
     * @param p_data opaque pointer for the callback
     * @param p_id id of the dialog
     */
    void callback(Pointer p_data, libvlc_dialog_id p_id);
}