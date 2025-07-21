package org.watermedia.videolan4j.discovery;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.binding.lib.LibC;
import org.watermedia.videolan4j.tools.Tools;

import java.util.regex.Pattern;

/**
 * Retains information for OS support. each OS has its own way to handle binaries, including for all Linux distros
 */
public enum Environment {
    /**
     * Windows environment implementation
     */
    WINDOWS(Tools.patterns("libvlc\\.dll", "libvlccore\\.dll"), "plugins/", "vlc/plugins/"),

    /**
     * MacOS environment implementation
     */
    MACOS(Tools.patterns("libvlc\\.dylib", "libvlccore\\.dylib"), "../plugins/"),

    /**
     * Linux environment implementation
     */
    LINUX(Tools.patterns("libvlc\\.so(?:\\.\\d)*", "libvlccore\\.so(?:\\.\\d)*"), "plugins/", "vlc/plugins/");

    /**
     * Returns the current raw OS name. Is based on a cheap call to system properties
     */
    public static String osName() {
        return System.getProperty("os.name");
    }

    /**
     * Provides the current directory environment. It uses JNA platform API to determinate if
     * the current OS is supported or has an implementation on it.
     */
    public static Environment get() {
        switch (Platform.getOSType()) {
            case Platform.WINDOWS:
                return WINDOWS;
            case Platform.MAC:
                return MACOS;
            case Platform.LINUX:
                return LINUX;
            default:
                return null;
        }
    }

    final Pattern[] filePatterns;
    final String[] pluginPaths;
    Environment(Pattern[] filePatterns, String... pluginPaths) {
        this.filePatterns = filePatterns;
        this.pluginPaths = pluginPaths;
    }

    public Pattern[] getFilePatterns() {
        return filePatterns;
    }

    public String[] getPluginPaths() {
        return pluginPaths;
    }

    /**
     * Sets an environment var, implementation might varies depending the OS
     */
    public boolean setVar(String k, String v) {
        return LibC.setEnv(k, v, 1) == 0;
    }
}
