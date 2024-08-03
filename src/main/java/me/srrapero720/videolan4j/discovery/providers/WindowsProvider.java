package me.srrapero720.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import me.srrapero720.videolan4j.discovery.ProviderPriority;

public class WindowsProvider extends DiscoveryPathProvider {
    private static final String INSTALL_PATH_WIN = "C:\\Program Files\\VideoLAN\\VLC";

    @Override
    public boolean supported() {
        return Platform.isWindows();
    }

    @Override
    public ProviderPriority priority() {
        return ProviderPriority.HIGH;
    }

    @Override
    public String[] directories() {
        return new String[] {
                INSTALL_PATH_WIN
        };
    }
}
