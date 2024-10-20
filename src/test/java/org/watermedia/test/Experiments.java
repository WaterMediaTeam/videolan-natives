package org.watermedia.test;

import org.junit.jupiter.api.Test;
import org.watermedia.videolan4j.VideoLan4J;
import org.watermedia.videolan4j.discovery.NativeDiscovery;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Experiments {
    @Test
    public void testDiscovery() {
        VideoLan4J.LOGGER.info("-------------- TEST 1 --------------");
        NativeDiscovery.discovery();
        assertTrue(NativeDiscovery.isDiscovered());
    }

    @Test
    public void testUri() {
        // VALIDATE THINGS BEFORE PUSH IT
        URI u = URI.create("file://path/to/protocol?var1=string");
        VideoLan4J.LOGGER.info("-------------- TEST 2 --------------");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());

        File f = new File("custom/directory/pointing to nowhere/");
        u = f.toURI();

        VideoLan4J.LOGGER.info("-------------- TEST 3 --------------");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());

        u = URI.create("water://local.wm/c:/users/J-RAP");

        VideoLan4J.LOGGER.info("-------------- TEST 4 --------------");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());
    }
}
