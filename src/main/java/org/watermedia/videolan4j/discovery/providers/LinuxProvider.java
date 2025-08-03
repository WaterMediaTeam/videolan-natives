package org.watermedia.videolan4j.discovery.providers;

import com.sun.jna.Platform;
import org.watermedia.videolan4j.tools.Tools;

import java.io.File;

public class LinuxProvider implements IProvider {
    @Override
    public boolean supported() {
        return Platform.isLinux();
    }

    @Override
    public Priority priority() {
        return Priority.NORMAL;
    }

    @Override
    public String[] directories() {
        final String[] ldPaths = System.getenv("LD_LIBRARY_PATH").split(File.pathSeparator);
        final String[] genericPaths = new String[] {
                "/usr/lib",
                "/usr/lib64",
                "/usr/lib/i386-linux-gnu",
                "/usr/lib/x86_64-linux-gnu",
                "/usr/bin",
                "/usr/bin64",
                "/usr/local/lib",
                "/usr/local/lib64",
                "/bin",
                "/lib"
        };
        return Tools.concat(ldPaths, genericPaths);
    }
}
