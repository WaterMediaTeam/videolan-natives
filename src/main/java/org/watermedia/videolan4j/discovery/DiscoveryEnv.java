package org.watermedia.videolan4j.discovery;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.binding.lib.LibC;

import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;

public enum DiscoveryEnv {
    WINDOWS (
            Platform::isWindows,
            new Pattern[] {
                    Pattern.compile("libvlc\\.dll"),
                    Pattern.compile("libvlccore\\.dll"),
            },
            new String[] {
                    "plugins/",
                    "vlc/plugins/"
            },
            (k, v) ->
                    LibC.INSTANCE._putenv(k + "=" + v) == 0
    ),

    MACOS (
            Platform::isMac,
            new Pattern[] {
                    Pattern.compile("libvlc\\.dylib"),
                    Pattern.compile("libvlccore\\.dylib"),
            },
            new String[] {
                    "../plugins/"
            },
            (k, v) ->
                    LibC.INSTANCE.setenv(k, v, 1) == 0
    ),

    LINUX (
            Platform::isLinux,
            new Pattern[] {
                    Pattern.compile("libvlc\\.so(?:\\.\\d)*"),
                    Pattern.compile("libvlccore\\.so(?:\\.\\d)*")
            },
            new String[] {
                    "plugins/",
                    "vlc/plugins/"
            },
            (k, v) ->
                    LibC.INSTANCE.setenv(k, v, 1) == 0
    );

    public static String osName() {
        return System.getProperty("os.name");
    }

    public static DiscoveryEnv get() {
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

    private final BooleanSupplier supported;
    private final Pattern[] binaryPatterns;
    private final String[] pluginsPath;
    private final BiFunction<String, String, Boolean> setEnvFunction;
    DiscoveryEnv(BooleanSupplier supported, Pattern[] binaryPatterns, String[] pluginsPath, BiFunction<String, String, Boolean> setEnvFunction) {
        this.supported = supported;
        this.binaryPatterns = binaryPatterns;
        this.pluginsPath = pluginsPath;
        this.setEnvFunction = setEnvFunction;
    }

    public boolean supported() {
        return supported.getAsBoolean();
    }

    public Pattern[] binaryPatterns() {
        return binaryPatterns;
    }

    public String[] pluginPaths() {
        return pluginsPath;
    }

    public boolean setEnvironmentVar(String k, String v) {
        return setEnvFunction.apply(k, v);
    }
}
