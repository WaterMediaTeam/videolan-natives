package me.srrapero720.videolan.natives;

import java.io.File;
import java.net.URI;

public class Experiments {
    public static void main(String... args) {
        // VALIDATE THINGS BEFORE PUSH IT
        URI u = URI.create("file://path/to/protocol?var1=string");
        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());

        File f = new File("");
        u = f.toURI();

        System.out.println("URI IS: " + u);
        System.out.println("HOST IS: " + u.getHost());
        System.out.println("AUTH IS: " + u.getAuthority());
        System.out.println("PROTOCOL IS: " + u.getScheme());
        System.out.println("PATH IS: " + u.getPath());
        System.out.println("QUERY IS: " + u.getQuery());
    }
}
