package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_dialog_display_question_cb extends Callback {

    /**
     * Called when a question dialog needs to be displayed
     * <p>
     * You can interact with this dialog by calling libvlc_dialog_post_action()
     * to post an answer or libvlc_dialog_dismiss() to cancel this dialog.
     * <p>
     * <em>to receive this callack, libvlc_dialog_cbs.pf_cancel should not be
     * NULL.</em>
     *
     * @param p_data opaque pointer for the callback
     * @param p_id id used to interact with the dialog
     * @param psz_title title of the dialog
     * @param psz_text text of the dialog
     * @param i_type question type (or severity) of the dialog
     * @param psz_cancel text of the cancel button
     * @param psz_action1 text of the first button, if NULL, don't display this button
     * @param psz_action2 text of the second button, if NULL, don't display this button
     */
    void callback(Pointer p_data, libvlc_dialog_id p_id, String psz_title, String psz_text, int i_type, String psz_cancel, String psz_action1, String psz_action2);
}