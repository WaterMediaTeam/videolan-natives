package org.watermedia.videolan4j.tools;

import com.sun.jna.Pointer;

import java.nio.ByteBuffer;

public class NativeBuffer {

    private final ByteBuffer buffer;
    private final Pointer pointer;

    public NativeBuffer(ByteBuffer buffer, Pointer pointer) {
        this.buffer = buffer;
        this.pointer = pointer;
    }

    public ByteBuffer buffer() {
        return this.buffer;
    }

    public Pointer pointer() {
        return this.pointer;
    }
}
