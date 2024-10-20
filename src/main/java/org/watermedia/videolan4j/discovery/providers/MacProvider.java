package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.discovery.DiscoveryProvider;

public class MacProvider implements DiscoveryProvider {
    @Override
    public boolean supported() {
        return Platform.isMac();
    }

    @Override
    public Priority priority() {
        return Priority.HIGH;
    }

    @Override
    public String[] directories() {
        return new String[] {
                "/Applications/VLC.app/Contents/Frameworks",
                "/Applications/VLC.app/Contents/MacOS/lib"
        };
    }
}
