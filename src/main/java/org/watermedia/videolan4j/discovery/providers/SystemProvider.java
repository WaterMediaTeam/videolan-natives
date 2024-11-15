package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;

import java.io.File;

public class SystemProvider implements IProvider {
    @Override
    public boolean supported() {
        return !Platform.isWindows();
    }

    @Override
    public Priority priority() {
        return Priority.LOWEST;
    }

    @Override
    public String[] directories() { // I JUST KEEP THIS FOR LINUX USERS... IF SOME WEIRDO INSTALLS ALL THEIR STUFF ON SYSTEM PATHS
        String path = System.getenv("PATH");
        if (path != null) {
            return path.split(File.pathSeparator);
        } else {
            return new String[0];
        }
    }
}
