package org.watermedia.videolan4j.discovery.providers;

import org.watermedia.videolan4j.discovery.DiscoveryProvider;

import java.io.File;

public class JnaPathProvider implements DiscoveryProvider {
    private static final String JNA_LIBRARY_PATH = System.getProperty("jna.library.path");

    @Override
    public boolean supported() {
        return JNA_LIBRARY_PATH != null;
    }

    @Override
    public Priority priority() {
        return Priority.NORMAL;
    }

    @Override
    public String[] directories() {
        return JNA_LIBRARY_PATH.split(File.pathSeparator);
    }
}
