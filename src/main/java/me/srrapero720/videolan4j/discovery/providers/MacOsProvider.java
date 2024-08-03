package me.srrapero720.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import me.srrapero720.videolan4j.discovery.ProviderPriority;

public class MacOsProvider extends DiscoveryPathProvider {
    @Override
    public boolean supported() {
        return Platform.isMac();
    }

    @Override
    public ProviderPriority priority() {
        return ProviderPriority.HIGH;
    }

    @Override
    public String[] directories() {
        return new String[] {
                "/Applications/VLC.app/Contents/Frameworks",
                "/Applications/VLC.app/Contents/MacOS/lib"
        };
    }
}
