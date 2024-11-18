package org.watermedia.videolan4j.discovery.providers;

import org.watermedia.videolan4j.discovery.NativeDiscovery;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;
import static org.watermedia.videolan4j.VideoLan4J.VLC4J_USER_DISCOVERY_PATH;

public class UserProvider implements IProvider {
    private static final File USER_DISCOVERY_PATH;

    @Override
    public Priority priority() {
        return Priority.OVERWRITE;
    }

    @Override
    public boolean supported() {
        return USER_DISCOVERY_PATH != null && USER_DISCOVERY_PATH.exists();
    }

    @Override
    public String[] directories() {
        return new String[] {
                USER_DISCOVERY_PATH.getAbsolutePath()
        };
    }

    static {
        String decodedPath;
        try {
            if (VLC4J_USER_DISCOVERY_PATH == null) throw new NullPointerException();
            decodedPath = URLDecoder.decode(VLC4J_USER_DISCOVERY_PATH, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            LOGGER.debug(NativeDiscovery.IT, "Cannot decode custom user path", e);
            decodedPath = null;
        }

        USER_DISCOVERY_PATH = decodedPath == null ? null : new File(decodedPath);
    }
}
