import java.io.InputStream;
import java.util.HashMap;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RouteBuilder {
    private HashMap<String, HashMap<String, HttpHandler>> routeMap = new HashMap<>();

    private HttpHandler getMergedHandler(String path) {
        HashMap<String, HttpHandler> methodHandlers = routeMap.get(path);

        if (methodHandlers == null) {
            return exchange -> exchange.sendResponseHeaders(404, -1);
        }

        return exchange -> {
            String method = exchange.getRequestMethod();
            String uri = exchange.getRequestURI().toString();

            System.out.printf("\n--- %s request on %s ---\n", method, uri);
            InputStream bodyStream = exchange.getRequestBody();
            byte[] body = new byte[bodyStream.available()];
            bodyStream.read(body);
            System.out.println(new String(body));

            HttpHandler handler = methodHandlers.get(method);

            if(handler == null) {
                exchange.sendResponseHeaders(405, -1);
            } else {
                handler.handle(exchange);
            }
        };
    } 

    public RouteBuilder addRoute(String path, String method, HttpHandler handler) {
        if (!routeMap.containsKey(path)) {
            routeMap.put(path, new HashMap<>());
        }
        routeMap.get(path).put(method, handler);
        System.out.printf("Registered handler for %s on %s - %s\n", method, path, routeMap.get(path).get(method));
        return this;
    }

    public void registerRoutes(HttpServer server) {
        for (var path : routeMap.keySet()) {
            server.createContext(path, getMergedHandler(path));
        }
    }
}
