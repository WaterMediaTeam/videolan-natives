package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.discovery.DiscoveryProvider;

public class WinProvider implements DiscoveryProvider {
    @Override
    public boolean supported() {
        return Platform.isWindows();
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public String[] directories() {
        return new String[] {
                "C:\\Program Files\\VideoLAN\\VLC"
        };
    }
}
