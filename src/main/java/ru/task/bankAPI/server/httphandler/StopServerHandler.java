package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.server.MyServer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StopServerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Server stopped by user";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
        MyServer.stopServer();
    }
}
