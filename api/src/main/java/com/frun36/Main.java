package com.frun36;

import com.sun.net.httpserver.HttpServer;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Dotenv env = Dotenv.load();
        String url = env.get("JDBC");
        
        Connection conn = DriverManager.getConnection(url);



        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/point", new BasicCrud<Tables.Point>(conn, Tables.Point.class, "point"));

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("App running on http://localhost:8080");
    }
}