package uk.co.caprica.vlcj.discovery.strategies;

import com.sun.jna.Platform;
import uk.co.caprica.vlcj.binding.lib.LibC;

import java.util.regex.Pattern;

public class WinStrategy extends DiscoveryStrategy {
    @Override
    public boolean supported() {
        return Platform.isWindows();
    }

    @Override
    public Pattern[] pathPatterns() {
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
