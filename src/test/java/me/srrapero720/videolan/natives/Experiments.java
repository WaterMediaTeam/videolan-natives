package me.srrapero720.videolan.natives;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Experiments {
    public static void main(String... args) throws MalformedURLException {
        // VALIDATE THINGS BEFORE PUSH IT
        URI u = URI.create("file:/c:/pa th/to/p roto col?var1=string");
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


        System.out.println("URI IS: " + new URL("file:///pa th/to/fil e.mp4"));
    }
}
