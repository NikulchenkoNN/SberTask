package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StopServerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String respone = "Server stopped by user";
        exchange.sendResponseHeaders(200, respone.length());
        OutputStream os = exchange.getResponseBody();
        os.write(respone.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
