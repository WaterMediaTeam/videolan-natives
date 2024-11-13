package org.watermedia.videolan4j.binding.internal;

import com.sun.jna.Callback;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;

/**
 * Native open media callback.
 */
public interface libvlc_media_open_cb extends Callback {

    /**
     * Callback prototype to open a custom bitstream input media.
     * <p>
     * The same media item can be opened multiple times. Each time, this callback
     * is invoked. It should allocate and initialize any instance-specific
     * resources, then store them in *datap. The instance resources can be freed
     * in the @ref libvlc_close_cb callback.
     * <p>
     * For convenience, datap is initially NULL and sizep is initially 0.
     *
     * @param opaque private pointer as passed to libvlc_media_new_callbacks()
     * @param datap storage space for a private data pointer [OUT]
     * @param sizep byte length of the bitstream or 0 if unknown [OUT]
     * @return 0 on success, non-zero on error. In case of failure, the other
     * callbacks will not be invoked and any value stored in datap and sizep is
     * discarded.
     */
    int open(Pointer opaque, PointerByReference datap, LongByReference sizep);
}