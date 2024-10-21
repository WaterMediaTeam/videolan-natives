package org.watermedia.videolan4j;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Factory for LibVLC general usages
 */
public class ByteBufferFactory {
    private static final Unsafe UNSAFE;
    private static final long ADDRESS_FIELD_OFFSET;
    static Function<Integer, ByteBuffer> BUFFER_ALLOCATOR = ByteBufferFactory::alloc1;
    static Consumer<ByteBuffer> BUFFER_DEALLOCATOR = ByteBufferFactory::dealloc1;

    /**
     * Allocates a new byte buffer.
     *
     * @param size required size for the buffer
     * @return aligned byte buffer
     */
    public static ByteBuffer alloc(int size) {
        return BUFFER_ALLOCATOR.apply(size);
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
        Buffer buffer = ByteBuffer.allocateDirect(size + VideoLan4J.LIBVLC_BUFFER_ALIGNMENT);
        long address = address(buffer);
        if (!isAligned(address)) {
            int newPosition = (int) (VideoLan4J.LIBVLC_BUFFER_ALIGNMENT - (address & (VideoLan4J.LIBVLC_BUFFER_ALIGNMENT - 1)));
            buffer.position(newPosition);
            size += newPosition;
        }
        ByteBuffer result = (ByteBuffer) buffer.limit(size);
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
     * Validates if the created ByteBuffer is properly aligned
     * @param address buffer address
     * @return true if is properly aligned
     */
    public static boolean isAligned(long address) {
        return (address & (VideoLan4J.LIBVLC_BUFFER_ALIGNMENT - 1)) == 0;
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
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
            ADDRESS_FIELD_OFFSET = UNSAFE.objectFieldOffset(Buffer.class.getDeclaredField("address"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
