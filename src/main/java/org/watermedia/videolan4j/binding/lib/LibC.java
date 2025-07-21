package org.watermedia.videolan4j.binding.lib;

import java.nio.ByteBuffer;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

/**
 * Minimal "C" library binding class.
 */
public interface LibC extends Library {
    LibC INSTANCE = Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"), LibC.class);

    /**
     * Format a string with a variable arguments list into a fixed size buffer.
     * <p>
     * The resultant string will be truncated to the size of the buffer if it would
     * otherwise exceed it.
     * <p>
     * For example, if the buffer has a capacity of 10 this is a maximum of 9
     * characters plus a null terminator for a total capacity of 10. This means if
     * 10 characters are required, the buffer capacity must be 11 to accommodate
     * the null terminator.
     *
     * @param str buffer
     * @param size capacity of the buffer, including space for a null terminator
     * @param format format string
     * @param args format arguments
     * @return length of the formatted string, which may exceed the capacity of the buffer, or less than zero on error
     */
    static int printf(ByteBuffer str, int size, String format, Pointer args) {
        return INSTANCE.vsnprintf(str, size, format, args);
    }

    /**
     * Locks (pins) parts of virtual address space into RAM so it can not be swapped out.
     *
     * @param addr address pointer
     * @param length length
     * @return 0 if successful; -1 if not, setting <code>errno</code> to an error code
     */
    static int memoryLock(Pointer addr, long length) {
        if (Platform.isWindows()) {
            // Windows does not support mlock, use VirtualLock instead
            return Kernel32.memoryLock(addr, length);
        }
        return INSTANCE.mlock(addr, new NativeLong(length));
    }

    /**
     * Unlock previously locked memory.
     *
     * @param addr address pointer
     * @param length length
     * @return 0 if successful; -1 if not, setting <code>errno</code> to an error code
     */
    static int memoryUnlock(Pointer addr, long length) {
        if (Platform.isWindows()) {
            // Windows does not support munlock, use VirtualUnlock instead
            return Kernel32.memoryUnlock(addr, length);
        }
        return INSTANCE.munlock(addr, new NativeLong(length));
    }

    /**
     * Change or add an environment variable.
     * <p>
     * The value strings are copied (natively).
     * <p>
     * <p>
     * Note that after setting an environment variable, it will <em>not</em> show up via
     * System#getenv even if it was successfully set.
     * <p>
     *
     * @param name name of environment variable
     * @param value value of the environment variable
     * @param overwrite non-zero to replace any existing value
     * @return 0 if successful; -1 if not, setting <code>errno</code> to an error code
     */
    static int setEnv(String name, String value, int overwrite) {
        if (Platform.isWindows()) {
            // Windows does not support setenv, use _putenv instead
            return INSTANCE._putenv(name + "=" + value);
        }
        return INSTANCE.setenv(name, value, overwrite);
    }

    /**
     * Unset an environment variable.
     * <p>
     * <em>Not available on Windows.</em>
     *
     * @param name name of environment variable
     * @return 0 if successful; -1 if not, setting <code>errno</code> to an error code
     */
    static int unsetEnv(String name) {
        if (Platform.isWindows()) {
            // Windows does not support unsetenv, use _putenv instead
            return INSTANCE._putenv(name + "=");
        }
        return INSTANCE.unsetenv(name);
    }

    /**
     * Get the current process id.
     * <p>
     * <em>Not available on Windows.</em>
     *
     * @return process id
     */
    static int getProcessId() {
        return INSTANCE.getpid();
    }

    // INTERFACE METHODS
    int vsnprintf(ByteBuffer str, int size, String format, Pointer args);
    int mlock(Pointer addr, NativeLong length);
    int munlock(Pointer addr, NativeLong length);
    int setenv(String name, String value, int overwrite);
    int unsetenv(String name);
    int getpid();
    int _putenv(String envstring);
}
