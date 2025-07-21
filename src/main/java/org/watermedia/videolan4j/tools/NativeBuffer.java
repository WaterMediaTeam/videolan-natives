package org.watermedia.videolan4j.tools;

import com.sun.jna.Pointer;

import java.nio.ByteBuffer;

public record NativeBuffer(ByteBuffer buffer, Pointer pointer) {
}
