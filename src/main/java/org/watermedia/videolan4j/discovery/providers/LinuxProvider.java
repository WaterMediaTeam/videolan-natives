package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.discovery.DiscoveryProvider;

public class LinuxProvider implements DiscoveryProvider {
    @Override
    public boolean supported() {
        return Platform.isLinux();
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public String[] directories() {
        return new String[] {
                "/usr/lib/x86_64-linux-gnu",
                "/usr/lib64",
                "/usr/local/lib64",
                "/usr/lib/i386-linux-gnu",
                "/usr/lib",
                "/usr/lib/vlc",
                "/usr/bin/",
                "/usr/bin/vlc",
                "/usr/local/lib",
                "/usr/local/lib/vlc",
                "/var/lib/flatpak",
                "/var/lib/flatpak/org.videolan.VLC",
                "/var/lib/flatpak/app/org.videolan.VLC",
                "/bin"
        };
    }
}
