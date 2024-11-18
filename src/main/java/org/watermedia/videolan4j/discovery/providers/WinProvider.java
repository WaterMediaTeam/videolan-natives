package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class WinProvider implements IProvider {
    private static final String VLC_REGISTRY_KEY = "SOFTWARE\\VideoLAN\\VLC";
    private static final String VLC_INSTALL_DIR_KEY = "InstallDir";

    @Override
    public boolean supported() {
        return Platform.isWindows();
    }

    @Override
    public Priority priority() {
        return Priority.NORMAL;
    }

    @Override
    public String[] directories() {
        return new String[] {
                Advapi32Util.registryGetStringValue(WinReg.HKEY_LOCAL_MACHINE, VLC_REGISTRY_KEY, VLC_INSTALL_DIR_KEY),
                "C:\\Program Files\\VideoLAN\\VLC"
        };
    }
}
