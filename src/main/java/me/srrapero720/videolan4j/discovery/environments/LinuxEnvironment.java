package me.srrapero720.videolan4j.discovery.environments;

import com.sun.jna.Platform;
import me.srrapero720.videolan4j.discovery.DiscoveryEnvironment;

import java.util.regex.Pattern;

public class LinuxEnvironment extends DiscoveryEnvironment {
    @Override
    public boolean supported() {
        return Platform.isLinux();
    }

    @Override
    public Pattern[] binPatterns() {
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
