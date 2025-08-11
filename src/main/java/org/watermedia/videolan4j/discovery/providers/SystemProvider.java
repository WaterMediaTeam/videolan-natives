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
        final String path = System.getenv("PATH");
        if (path != null) {
            // SPECIAL PRE-COMPUTE TO JUMP BACK LIB OR BIN FOLDERS, SO DISCOVERY CAN SEARCH IN BOTH
            final String[] paths = path.split(File.pathSeparator);
            for (int i = 0; i < paths.length; i++) {
                if (paths[i].endsWith(File.separatorChar + "bin") || paths[i].endsWith(File.pathSeparator + "lib")) {
                    paths[i] = paths[i].substring(0, paths[i].length() - File.pathSeparator.length() + 3);
                }
            }
            return paths;
        } else {
            return new String[0];
        }
    }
}
