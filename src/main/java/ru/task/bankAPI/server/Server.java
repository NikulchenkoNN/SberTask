package ru.task.bankAPI.server;

import com.sun.net.httpserver.HttpServer;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.httphandler.CreateCardHandler;
import ru.task.bankAPI.httphandler.CreateUserHandler;
import ru.task.bankAPI.httphandler.ShowCartsHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/newCard", new CreateCardHandler());
        server.createContext("/showCards", new ShowCartsHandler());
        server.createContext("/newUser", new CreateUserHandler());
        DataSourceHelper.createDb();
        server.start();
    }
}
