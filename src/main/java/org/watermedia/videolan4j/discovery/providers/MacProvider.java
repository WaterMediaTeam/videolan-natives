package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;

public class MacProvider implements IProvider {
    @Override
    public boolean supported() {
        return Platform.isMac();
    }

    @Override
    public Priority priority() {
        return Priority.HIGHEST;
    }

    @Override
    public String[] directories() {
        return new String[] {
                "/Applications/VLC.app/Contents/Frameworks",
                "/Applications/VLC.app/Contents/MacOS/lib"
        };
    }
}
