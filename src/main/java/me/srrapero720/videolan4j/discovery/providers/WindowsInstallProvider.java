package me.srrapero720.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;
import me.srrapero720.videolan4j.discovery.ProviderPriority;

public class WindowsInstallProvider extends DiscoveryPathProvider {
    private static final String VLC_REGISTRY_KEY = "SOFTWARE\\VideoLAN\\VLC";
    private static final String VLC_INSTALL_DIR_KEY = "InstallDir";

    @Override
    public boolean supported() {
        return Platform.isWindows();
    }

    @Override
    public ProviderPriority priority() {
        return ProviderPriority.HIGHEST;
    }

    @Override
    public String[] directories() {
        try {
            return new String[] {
                    Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, VLC_REGISTRY_KEY, VLC_INSTALL_DIR_KEY)
            };
        } catch (Exception ignored) {}

        return new String[0];
    }
}
