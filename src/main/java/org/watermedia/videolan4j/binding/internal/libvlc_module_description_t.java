package org.watermedia.videolan4j.binding.internal;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

/**
 * Description of a module.
 */
public class libvlc_module_description_t extends Structure {
    public static class ByReference extends libvlc_module_description_t implements Structure.ByReference {}

    public String psz_name;
    public String psz_shortname;
    public String psz_longname;
    public String psz_help;
    public libvlc_module_description_t.ByReference p_next;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("psz_name", "psz_shortname", "psz_longname", "psz_help", "p_next");
    }
}