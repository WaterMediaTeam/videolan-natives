package org.watermedia.videolan4j.binding.lib;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface Kernel32 extends StdCallLibrary {

    Kernel32 INSTANCE = Native.loadLibrary("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

    /**
     * Locks (pins) parts of virtual address space into RAM so it can not be swapped out.
     *
     * @param lpAddress address pointer
     * @param dwSize length
     * @return 0 if successful; -1 if not, setting <code>errno</code> to an error code
     */
    int VirtualLock(Pointer lpAddress, size_t dwSize);

    /**
     * Unlock previously locked memory.
     *
     * @param lpAddress address pointer
     * @param dwSize length
     * @return Zero if successful; -1 if not, setting <code>errno</code> to an error code
     */
    int VirtualUnlock(Pointer lpAddress, size_t dwSize);

    /**
     * Allocates a block of memory from a specified heap.
     *
     * @param hHeap Handle to the heap from which the memory will be allocated.
     * @param dwFlags Allocation options.
     * @param dwBytes Number of bytes to allocate.
     * @return Pointer to the allocated memory block, or NULL if the allocation fails.
     */
    Pointer HeapAlloc(Pointer hHeap, int dwFlags, size_t dwBytes);

    /**
     * Creates a new heap object.
     *
     * @param flOptions Options for heap creation.
     * @param dwInitialSize Initial size of the heap, in bytes.
     * @param dwMaximumSize Maximum size of the heap, in bytes.
     * @return Handle to the new heap, or NULL if creation fails.
     */
    Pointer HeapCreate(int flOptions, size_t dwInitialSize, size_t dwMaximumSize);

    /**
     * Reallocates a block of memory from a specified heap.
     *
     * @param hHeap Handle to the heap from which the memory was allocated.
     * @param dwFlags Reallocation options.
     * @param lpMem Pointer to the memory block to be reallocated.
     * @param dwBytes New size of the memory block, in bytes.
     * @return Pointer to the reallocated memory block, or NULL if reallocation fails.
     */
    Pointer HeapReAlloc(Pointer hHeap, int dwFlags, Pointer lpMem, size_t dwBytes);

    /**
     * Frees a block of memory allocated from a specified heap.
     *
     * @param hHeap Handle to the heap from which the memory was allocated.
     * @param dwFlags Options for freeing the memory (must be zero).
     * @param lpMem Pointer to the memory block to be freed.
     * @return TRUE if successful; FALSE if not.
     */
    boolean HeapFree(Pointer hHeap, int dwFlags, Pointer lpMem);
}