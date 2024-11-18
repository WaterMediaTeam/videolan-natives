package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;

public class LinuxProvider implements IProvider {
    @Override
    public boolean supported() {
        return Platform.isLinux();
    }

    @Override
    public Priority priority() {
        return Priority.NORMAL;
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
                "/bin",
                "/bin/vlc"
        };
    }
}
