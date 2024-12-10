import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        RouteBuilder routeBuilder = new RouteBuilder();

        routeBuilder
                .addRoute("/", "GET", new FileHandler("static/index.html"))
                .addRoute("/test", "GET", new FileHandler("static/test.html"))
                .addRoute("/test", "POST", new FileHandler("static/index.html"));
        routeBuilder.registerRoutes(server);

        server.setExecutor(null); // Default executor
        server.start();
        System.out.println("App running on http://localhost:8080");
    }
}