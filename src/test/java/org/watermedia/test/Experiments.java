package org.watermedia.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Experiments {
    public static void main(String... args) throws MalformedURLException {


        // VALIDATE THINGS BEFORE PUSH IT
        URI u = URI.create("file://path/to/protocol?var1=string");
        System.out.println("---------- TEST 1 ------------");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());

        File f = new File("custom/directory/pointing to nowhere/");
        u = f.toURI();

        System.out.println("---------- TEST 2 ------------");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());

        u = URI.create("water://local.wm/c:/users/J-RAP");

        System.out.println("---------- TEST 3 ------------");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());
    }
}
