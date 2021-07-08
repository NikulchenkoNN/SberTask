package ru.task.bankAPI.server;

import com.sun.net.httpserver.HttpServer;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.server.httphandler.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyServer {
    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/newCard", new CreateCardHandler());
        server.createContext("/showCards", new ShowCardsHandler());
        server.createContext("/newUser", new CreateUserHandler());
        server.createContext("/updateBalance", new UpdateBalanceHandler());
        server.createContext("/getBalance", new GetBalanceHandler());
        DataSourceHelper.createDb();
        server.start();
    }
}
