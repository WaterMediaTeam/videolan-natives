package org.watermedia.videolan4j.tools;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.watermedia.videolan4j.VideoLan4J;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

/**
 * Factory for LibVLC general usages
 */
public class Buffers {
    private static final Marker IT = MarkerManager.getMarker(Buffers.class.getSimpleName());
    private static final Unsafe UNSAFE;
    private static final long ADDRESS_FIELD_OFFSET;
    private static Function<Integer, ByteBuffer> BUFFER_ALLOCATOR = Buffers::alloc1;
    private static Consumer<ByteBuffer> BUFFER_DEALLOCATOR = Buffers::dealloc1;

    public static void setBufferAllocator(Function<Integer, ByteBuffer> bufferAllocator) {
        BUFFER_ALLOCATOR = bufferAllocator;
    }

    public static void setBufferDeallocator(Consumer<ByteBuffer> bufferDeallocator) {
        BUFFER_DEALLOCATOR = bufferDeallocator;
    }

    /**
     * Allocates a new byte buffer.
     *
     * @param size required size for the buffer
     * @return aligned byte buffer
     */
    public static ByteBuffer alloc(int size) {
        ByteBuffer buffer = BUFFER_ALLOCATOR.apply(size);
        if (!isAligned(address(buffer))) {
            LOGGER.warn(IT, "Buffer address {} with size {} is unaligned, forcing and alignment", address(buffer), size);
            buffer = align(buffer, address(buffer), size);
        }
        return buffer;
    }

    /**
     * Deallocates existing byte buffer
     *
     * @param buffer buffer to release
     */
    public static void dealloc(ByteBuffer buffer) {
        BUFFER_DEALLOCATOR.accept(buffer);
    }

    /**
     * Default buffer deallocator
     * <p>Method is NO-OP but is reserved for future usages</p>
     * @param buffer buffer to deallocate
     */
    static void dealloc1(ByteBuffer buffer) {

    }

    /**
     * Default buffer allocator and alignment
     * @param size buffer size
     * @return byte buffer instance
     */
    static ByteBuffer alloc1(int size) {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(size + VideoLan4J.LIBVLC_BUFFER_ALIGNMENT);
        final long address = address(buffer);
        return align(buffer, address, size);
    }

    /**
     * Validates if the created ByteBuffer is properly aligned
     * @param address buffer address
     * @return true if is properly aligned
     */
    public static boolean isAligned(long address) {
        return (address & (VideoLan4J.LIBVLC_BUFFER_ALIGNMENT - 1)) == 0;
    }

    /**
     * Get the address of the direct buffer.
     * <p>
     * This method is unsafe and should be used with caution.
     *
     * @param buffer buffer to get
     * @return memory address pointer
     */
    public static ByteBuffer align(ByteBuffer buffer, long address, int size) {
        if (!isAligned(address)) {
            final int newPosition = (int) (VideoLan4J.LIBVLC_BUFFER_ALIGNMENT - (address & (VideoLan4J.LIBVLC_BUFFER_ALIGNMENT - 1)));
            buffer.position(newPosition);
            size += newPosition;
        }
        final ByteBuffer result = buffer.limit(size);
        return result.slice().order(ByteOrder.nativeOrder());
    }

    /**
     * Get the address of the direct buffer.
     *
     * @param buffer buffer to get
     * @return memory address pointer
     */
    public static long address(ByteBuffer buffer) {
        return UNSAFE.getLong(buffer, ADDRESS_FIELD_OFFSET);
    }

    /**
     * Get the address of the direct buffer.
     *
     * @param buffer buffer to get
     * @return memory address pointer
     */
    public static long address(Buffer buffer) {
        return UNSAFE.getLong(buffer, ADDRESS_FIELD_OFFSET);
    }

    static {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            ADDRESS_FIELD_OFFSET = UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
