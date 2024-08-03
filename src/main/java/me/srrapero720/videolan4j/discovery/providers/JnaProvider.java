package me.srrapero720.videolan4j.discovery.providers;

import me.srrapero720.videolan4j.discovery.ProviderPriority;

import java.io.File;

public class JnaProvider extends DiscoveryPathProvider {
    private static final String JNA_LIBRARY_PATH = System.getProperty("jna.library.path");

    @Override
    public boolean supported() {
        return JNA_LIBRARY_PATH != null;
    }

    @Override
    public ProviderPriority priority() {
        return ProviderPriority.NORMAL;
    }

    @Override
    public String[] directories() {
        return JNA_LIBRARY_PATH.split(File.pathSeparator);
    }
}
