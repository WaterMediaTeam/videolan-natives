package org.watermedia.videolan4j.discovery.providers;

import java.io.File;

public class JnaLibProvider implements IProvider {
    private static final String JNA_LIBRARY_PATH = System.getProperty("jna.library.path");

    @Override
    public boolean supported() {
        return JNA_LIBRARY_PATH != null;
    }

    @Override
    public Priority priority() {
        return Priority.LOW;
    }

    @Override
    public String[] directories() {
        return JNA_LIBRARY_PATH.split(File.pathSeparator);
    }
}
