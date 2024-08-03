package me.srrapero720.videolan4j.discovery.providers;

import me.srrapero720.videolan4j.discovery.ProviderPriority;

import java.io.File;

public class SystemProvider extends DiscoveryPathProvider {
    @Override
    public boolean supported() {
        return true;
    }

    @Override
    public ProviderPriority priority() {
        return ProviderPriority.LOWEST;
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
