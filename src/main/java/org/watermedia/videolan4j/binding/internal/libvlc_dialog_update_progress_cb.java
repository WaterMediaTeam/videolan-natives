package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_dialog_update_progress_cb extends Callback {

    /**
     * Called when a progress dialog needs to be updated
     *
     * @param p_data opaque pointer for the callback
     * @param p_id id of the dialog
     * @param f_position osition of the progress bar (between 0.0 and 1.0)
     * @param psz_text new text of the progress dialog
     */
    void callback(Pointer p_data, libvlc_dialog_id p_id, float f_position, String psz_text);
}