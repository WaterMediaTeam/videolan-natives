package org.watermedia.videolan4j.discovery.environments;

import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import org.watermedia.videolan4j.discovery.DiscoveryEnvironment;
import org.watermedia.videolan4j.discovery.DiscoveryProvider;
import org.watermedia.videolan4j.VideoLan4J;

import java.util.regex.Pattern;

public class MacEnvironment extends DiscoveryEnvironment {
    @Override
    public boolean supported() {
        return Platform.isMac();
    }

    @Override
    public Pattern[] binPatterns() {
        return new Pattern[] {
                Pattern.compile("libvlc\\.dylib"),
                Pattern.compile("libvlccore\\.dylib"),
        };
    }

    @Override
    public String[] pluginPaths() {
        return new String[] {"../plugins"};
    }

    @Override
    protected boolean onFound(DiscoveryProvider provider, String path) {
        if (Boolean.parseBoolean(System.getProperty("videolan4j.disableMacWorkarounds"))) {
            NativeLibrary.addSearchPath(VideoLan4J.LIBVLCCORE_NAME, path);
            NativeLibrary.getInstance(VideoLan4J.LIBVLCCORE_NAME);
        }
        return super.onFound(provider, path);
    }
}
