/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009-2019 Caprica Software Limited.
 */

package uk.co.caprica.vlcj.binding.support.strings;

import com.sun.jna.Pointer;
import uk.co.caprica.vlcj.binding.lib.LibVlc;

/**
 * Encapsulation of access to native strings.
 * <p>
 * This class takes care of freeing the memory that was natively allocated for the string, if needed.
 * <p>
 * Generally, any native API that returns "const char*" must <em>not</em> be freed, so in such cases the native return
 * type mapping can actually be String rather than {@link Pointer}. Alternatively, {@link #copy(Pointer)}
 * can be used.
 * <p>
 * Generally, Any native API that returns "char*" <em>must</em> be freed, so in such cases the native return type
 * mapping must be {@link Pointer} and {@link #copyAndFree(Pointer)} must be used.
 * <p>
 * Where a native string is contained in a {@link com.sun.jna.Structure} those strings should <em>not</em> be freed if
 * the structure itself is subsequently freed (usually by a companion release native method), so in these cases
 * {@link #copy(Pointer)} must be used.
 */
public class NativeString {

    /**
     * Get a String from a native string pointer, freeing the native string pointer when done.
     * <p>
     * If the native string pointer is not freed then a native memory leak will occur.
     * <p>
     * Use this method if the native string type is "char*", i.e. lacking the "const" modifier.
     *
     * @param pointer pointer to native string, may be <code>null</code>
     * @return string, or <code>null</code> if the pointer was <code>null</code>
     */
    public static String copyAndFree(Pointer pointer) {
        try {
            return copy(pointer);
        } finally {
            free(pointer);
        }
    }

    /**
     * frees the native string pointer
     * <p>
     * If the native string pointer is not freed then a native memory leak will occur.
     * <p>
     * Use this method if the native string type is "char*", i.e. lacking the "const" modifier.
     * @param pointer pointer to native string, may be <code>null</code>
     */
    public static void free(Pointer pointer) {
        if (pointer != null) {
            LibVlc.libvlc_free(pointer);
        }
    }

    /**
     * Copy a String from a native string pointer, without freeing the native pointer.
     * <p>
     * Use this method if the native string type is "const char*".
     *
     * @param pointer pointer to native string, may be <code>null</code>
     * @return string, or <code>null</code> if the pointer was <code>null</code>
     */
    public static String copy(Pointer pointer) {
        return (pointer != null) ? pointer.getString(0) : null;
    }
}