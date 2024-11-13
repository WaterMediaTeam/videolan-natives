package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;

public interface libvlc_dialog_display_error_cb extends Callback {

    /**
     * Called when an error message needs to be displayed
     *
     * @param p_data opaque pointer for the callback
     * @param psz_title title of the diaog
     * @param psz_text text of the dialog
     */
    void callback(Pointer p_data, String psz_title, String psz_text);
}