package uk.co.caprica.vlcj.discovery.strategies;

import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import uk.co.caprica.vlcj.VideoLan4J;
import uk.co.caprica.vlcj.discovery.providers.DiscoveryPathProvider;

import java.util.regex.Pattern;

public class MacOsStrategy extends DiscoveryStrategy {
    @Override
    public boolean supported() {
        return Platform.isMac();
    }

    @Override
    public Pattern[] pathPatterns() {
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
    public boolean onFound(DiscoveryPathProvider provider, String path) {
        // TODO: option to disable macos workarrounds
        NativeLibrary.addSearchPath(VideoLan4J.LIBVLCCORE_NAME, path);
        NativeLibrary.getInstance(VideoLan4J.LIBVLCCORE_NAME);
        return super.onFound(provider, path);
    }
}
