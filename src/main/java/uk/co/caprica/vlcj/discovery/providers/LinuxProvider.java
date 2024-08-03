package uk.co.caprica.vlcj.discovery.providers;

import com.sun.jna.Platform;
import uk.co.caprica.vlcj.discovery.ProviderPriority;

public class LinuxProvider extends DiscoveryPathProvider {
    @Override
    public boolean supported() {
        return Platform.isLinux();
    }

    @Override
    public ProviderPriority priority() {
        return ProviderPriority.HIGH;
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
