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

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 *
 */
public class libvlc_media_track_t extends Structure {

    private static final List<String> FIELD_ORDER = Arrays.asList("i_codec", "i_original_fourcc", "i_id", "i_type", "i_profile", "i_level", "u", "i_bitrate", "psz_language", "psz_description");

    public int i_codec;
    public int i_original_fourcc;
    public int i_id;
    public int i_type;

    public int i_profile;
    public int i_level;

    public libvlc_media_track_u.ByReference u;

    public int i_bitrate;
    public Pointer psz_language;
    public Pointer psz_description;

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }
}
