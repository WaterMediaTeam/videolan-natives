package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_dialog_display_progress_cb extends Callback {

    /**
     * Called when a progress dialog needs to be displayed
     * <p>
     * If cancellable (psz_cancel != NULL), you can cancel this dialog by
     * calling libvlc_dialog_dismiss()
     * <p>
     * <em>to receive this callack, libvlc_dialog_cbs.pf_cancel and
     * libvlc_dialog_cbs.pf_update_progress should not be NULL.</em>
     *
     * @param p_data opaque pointer for the callback
     * @param p_id id used to interact with the dialog
     * @param psz_title title of the diaog
     * @param psz_text text of the dialog
     * @param b_indeterminate true if the progress dialog is indeterminate
     * @param f_position initial position of the progress bar (between 0.0 and 1.0)
     * @param psz_cancel text of the cancel button, if NULL the dialog is not cancellable
     */
    void callback(Pointer p_data, libvlc_dialog_id p_id, String psz_title, String psz_text, int b_indeterminate, float f_position, String psz_cancel);
}