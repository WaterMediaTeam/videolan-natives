package me.srrapero720.videolan4j.discovery;

public interface DiscoveryProvider {

    /**
     * By default, returns the simple class name
     * @return Name of the provider
     */
    default String name() {
        return getClass().getSimpleName();
    }

    /**
     * Order priority
     * @return
     */
    Priority priority();

    /**
     * Determines the support state
     * @return If was supported in the current environment
     */
    boolean supported();

    /**
     * Provides a list of the "look up here" directories.
     * @return array of paths
     */
    String[] directories();

    enum Priority {
        OVERWRITE,
        HIGHEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST,
    }
}
