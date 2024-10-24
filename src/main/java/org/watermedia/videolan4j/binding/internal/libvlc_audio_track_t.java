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
 *
 */
public class libvlc_audio_track_t extends Structure {
    private static final List<String> FIELD_ORDER = Arrays.asList("i_channels", "i_rate");

    public static class ByValue extends libvlc_audio_track_t implements Structure.ByValue {}

    public int i_channels;
    public int i_rate;

    @Override
    protected List<String> getFieldOrder() {
        return FIELD_ORDER;
    }
}
