package me.srrapero720.videolan4j.discovery.strategies;

import com.sun.jna.Platform;

import java.util.regex.Pattern;

public class LinuxStrategy extends DiscoveryStrategy {
    @Override
    public boolean supported() {
        return Platform.isLinux();
    }

    @Override
    public Pattern[] pathPatterns() {
        return new Pattern[] {
                Pattern.compile("libvlc\\.so(?:\\.\\d)*"),
                Pattern.compile("libvlccore\\.so(?:\\.\\d)*")
        };
    }

    @Override
    public String[] pluginPaths() {
        return new String[] {
                "plugins/",
                "vlc/plugins/"
        };
    }
}
