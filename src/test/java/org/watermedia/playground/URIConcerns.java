package org.watermedia.playground;

import java.io.File;
import java.net.URI;

import static org.watermedia.videolan4j.VideoLan4J.LOGGER;

public class URIConcerns {

    public void testUri() {
        URI u = URI.create("file://path/to/protocol?var1=string");
        LOGGER.info("-------------- TEST 2 --------------");
        System.out.println("URI IS: " + u); // file://path/to/protocol?var1=string
        System.out.println("HOST IS: " + u.getHost()); // path
        System.out.println("AUTH IS: " + u.getAuthority()); // path
        System.out.println("PROTOCOL IS: " + u.getScheme()); // file
        System.out.println("PATH IS: " + u.getPath()); // /to/protocol
        System.out.println("QUERY IS: " + u.getQuery()); // var1=string

        File f = new File("custom/directory/pointing to nowhere/");
        u = f.toURI();

        LOGGER.info("-------------- TEST 3 --------------");
        System.out.println("URI IS: " + u); // file:/A:/developer-code/libraries/videolan-natives/custom/directory/pointing%20to%20nowhere
        System.out.println("HOST IS: " + u.getHost()); // null
        System.out.println("AUTH IS: " + u.getAuthority()); // null
        System.out.println("PROTOCOL IS: " + u.getScheme()); // file
        System.out.println("PATH IS: " + u.getPath()); // /A:/developer-code/libraries/videolan-natives/custom/directory/pointing to nowhere
        System.out.println("QUERY IS: " + u.getQuery()); // null

        u = URI.create("water://local/c:/users/J-RAP");

        LOGGER.info("-------------- TEST 4 --------------");
        System.out.println("URI IS: " + u); // water://local.wm/c:/users/J-RAP
        System.out.println("HOST IS: " + u.getHost()); // local.wm
        System.out.println("AUTH IS: " + u.getAuthority()); // local.wm
        System.out.println("PROTOCOL IS: " + u.getScheme()); // water
        System.out.println("PATH IS: " + u.getPath()); // /c:/users/J-RAP
        System.out.println("QUERY IS: " + u.getQuery()); // null
    }
}
