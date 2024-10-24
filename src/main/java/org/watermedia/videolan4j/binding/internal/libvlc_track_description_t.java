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

package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/**
 * Encapsulation of a track description.
 */
public class libvlc_track_description_t extends Structure {

    private static final List<String> FIELD_ORDER = Arrays.asList("i_id", "psz_name", "p_next");

    public static class ByReference extends libvlc_track_description_t implements Structure.ByReference {}

    public int i_id;
    public String psz_name;
    public libvlc_track_description_t.ByReference p_next;

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }
}
