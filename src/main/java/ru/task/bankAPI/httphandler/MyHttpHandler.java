package ru.task.bankAPI.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;


public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String request = null;
        if ("GET".equals(exchange.getRequestMethod())) {
            request = handleGetRequest(exchange);
        } else if ("POST".equals(exchange.getRequestMethod())) {
            request = handlePostRequest(exchange);
        }
        handleResponse(exchange, request);
    }



    private String handlePostRequest(HttpExchange exchange) {
        return exchange.getRequestURI().toString();
    }

    private String handleGetRequest(HttpExchange exchange) {
        return exchange.getRequestURI().toString();
    }

    private void handleResponse(HttpExchange exchange, String request) {
        try {
            exchange.sendResponseHeaders(200, request.substring(request.lastIndexOf("/")+1).length());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(request.substring(request.lastIndexOf("/")+1).getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
