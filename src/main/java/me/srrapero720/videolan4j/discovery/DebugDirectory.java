package me.srrapero720.videolan4j.discovery;

import java.io.File;

public class DebugDirectory {
    private final String path;
    private final boolean exists;
    private final boolean directory;
    private final boolean readable;
    private final boolean executable;
    private final boolean hidden;

    public DebugDirectory(File file) {
        this.path = file.toPath().toString();
        this.exists = file.exists();
        this.directory = file.isDirectory();
        this.readable = file.canRead();
        this.executable = file.canExecute();
        this.hidden = file.isHidden();
    }

    @Override
    public String toString() {
        return "DebugDirectory{" +
                "path='" + path + '\'' +
                ", exists=" + exists +
                ", directory=" + directory +
                ", readable=" + readable +
                ", executable=" + executable +
                ", hidden=" + hidden +
                '}';
    }
}
