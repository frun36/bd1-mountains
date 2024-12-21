package com.frun36;

import com.sun.net.httpserver.HttpServer;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.frun36.model.*;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        Dotenv env = Dotenv.load();
        String url = env.get("JDBC");

        Connection conn = DriverManager.getConnection(url);
        conn.createStatement().execute("SET search_path TO mountains");
        System.out.println("Connected to DB");

        Integer port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/point", new BasicCrud<Point>(conn, Point.class, "point"));
        server.createContext("/trail", new BasicCrud<Point>(conn, Point.class, "trail"));
        server.createContext("/route", new BasicCrud<Point>(conn, Point.class, "route"));
        server.createContext("/route_point", new BasicCrud<Point>(conn, Point.class, "route_point"));

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("App running on http://localhost:" + port);
    }
}