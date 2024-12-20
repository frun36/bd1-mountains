package com.frun36;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/test", new BasicCrud<Tables.Point>(Tables.Point.class));

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("App running on http://localhost:8080");
    }
}