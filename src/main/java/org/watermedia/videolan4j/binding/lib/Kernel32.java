package org.watermedia.videolan4j.binding.lib;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Kernel32 extends StdCallLibrary {
    Kernel32 INSTANCE = Native.loadLibrary("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

    /**
     * Locks (pins) parts of virtual address space into RAM so it can not be swapped out.
     *
     * @param addr address pointer
     * @param length length
     * @return 0 if successful; -1 if not, setting <code>errno</code> to an error code
     */
    static int memoryLock(final Pointer addr, final long length) {
        if (!Platform.isWindows()) {
            return LibC.memoryLock(addr, length); // Use LibC for non-Windows platforms
        }
        return INSTANCE.VirtualLock(addr, new size_t(length));
    }

    /**
     * Unlock previously locked memory.
     *
     * @param addr address pointer
     * @param length length
     * @return Zero if successful; -1 if not, setting <code>errno</code> to an error code
     */
    static int memoryUnlock(final Pointer addr, final long length) {
        if (!Platform.isWindows()) {
            return LibC.memoryUnlock(addr, length); // Use LibC for non-Windows platforms
        }
        return INSTANCE.VirtualUnlock(addr, new size_t(length));
    }

    /**
     * Allocates a block of memory from a specified heap.
     *
     * @param heap Handle to the heap from which the memory will be allocated.
     * @param flags Allocation options.
     * @param length Number of bytes to allocate.
     * @return Pointer to the allocated memory block, or NULL if the allocation fails.
     */
    static Pointer heapAlloc(final Pointer heap, final int flags, final size_t length) {
        return INSTANCE.HeapAlloc(heap, flags, length);
    }

    /**
     * Creates a new heap object.
     *
     * @param options Options for heap creation.
     * @param length Initial size of the heap, in bytes.
     * @param maxLength Maximum size of the heap, in bytes.
     * @return Handle to the new heap, or NULL if creation fails.
     */
    static Pointer heapCreate(final int options, final size_t length, final size_t maxLength) {
        return INSTANCE.HeapCreate(options, length, maxLength);
    }

    /**
     * Reallocates a block of memory from a specified heap.
     *
     * @param heap Handle to the heap from which the memory was allocated.
     * @param flags Reallocation options.
     * @param pointer Pointer to the memory block to be reallocated.
     * @param length New size of the memory block, in bytes.
     * @return Pointer to the reallocated memory block, or NULL if reallocation fails.
     */
    static Pointer heapReAlloc(final Pointer heap, final int flags, final Pointer pointer, final size_t length) {
        return INSTANCE.HeapReAlloc(heap, flags, pointer, length);
    }

    /**
     * Frees a block of memory allocated from a specified heap.
     *
     * @param heap Handle to the heap from which the memory was allocated.
     * @param flags Options for freeing the memory (must be zero).
     * @param pointer Pointer to the memory block to be freed.
     * @return TRUE if successful; FALSE if not.
     */
    static boolean heapFree(final Pointer heap, final int flags, final Pointer pointer) {
        return INSTANCE.HeapFree(heap, flags, pointer);
    }


    int VirtualLock(Pointer addr, size_t length);
    int VirtualUnlock(Pointer addr, size_t length);
    Pointer HeapAlloc(Pointer heap, int flags, size_t length);
    Pointer HeapCreate(int options, size_t length, size_t maxLength);
    Pointer HeapReAlloc(Pointer heap, int flags, Pointer pointer, size_t length);
    boolean HeapFree(Pointer heap, int flags, Pointer pointer);
}