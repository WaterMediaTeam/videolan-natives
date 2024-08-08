package me.srrapero720.videolan4j.discovery.environments;

import com.sun.jna.Platform;
import me.srrapero720.videolan4j.binding.lib.LibC;
import me.srrapero720.videolan4j.discovery.DiscoveryEnvironment;

import java.util.regex.Pattern;

public class WinEnvironment extends DiscoveryEnvironment {
    @Override
    public boolean supported() {
        return Platform.isWindows();
    }

    @Override
    public Pattern[] binPatterns() {
        return new Pattern[] {
                Pattern.compile("libvlc\\.so(?:\\.\\d)*"),
                Pattern.compile("libvlccore\\.so(?:\\.\\d)*"),
        };
    }

    @Override
    public String[] pluginPaths() {
        return new String[] {
                "plugins/",
                "vlc/plugins/"
        };
    }

    @Override
    protected boolean setPluginPath(String path) {
        // TODO: VALIDATE IF REQUIRE _putenv instead of setEnv OR IF JNA PROVIDES A ALTERNATIVE
        return LibC.INSTANCE._putenv(PLUGIN_ENV_NAME + "=" + path) == 0;
    }
}
