import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class FileHandler implements HttpHandler {    
    String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
            exchange.sendResponseHeaders(200, fileBytes.length);
            
            OutputStream os = exchange.getResponseBody();
            os.write(fileBytes);
            os.close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(404, -1);
        }
    }
}
