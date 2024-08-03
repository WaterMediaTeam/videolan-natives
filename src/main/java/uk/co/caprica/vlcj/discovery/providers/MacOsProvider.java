package uk.co.caprica.vlcj.discovery.providers;

import com.sun.jna.Platform;
import uk.co.caprica.vlcj.discovery.ProviderPriority;

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
