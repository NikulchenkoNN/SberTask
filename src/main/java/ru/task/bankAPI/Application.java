package ru.task.bankAPI;

import com.sun.net.httpserver.HttpServer;
import ru.task.bankAPI.httphandler.MyHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;


public class Application {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.setExecutor(null);
        server.createContext("/api/hello", new MyHttpHandler());
        server.createContext("/api/bye", new MyHttpHandler());
        server.setExecutor(null);
        server.start();

    }
}
