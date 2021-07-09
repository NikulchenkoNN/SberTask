package ru.task.bankAPI.server;

import com.sun.net.httpserver.HttpServer;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.server.httphandler.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyServer {
    static HttpServer server;

    static {
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void startServer() {
        server.createContext("/newCard", new CreateCardHandler());
        server.createContext("/showCards", new ShowCardsHandler());
        server.createContext("/newUser", new CreateUserHandler());
        server.createContext("/updateBalance", new UpdateBalanceHandler());
        server.createContext("/getBalance", new GetBalanceHandler());
        server.createContext("/stopServer", new StopServerHandler());
        DataSourceHelper.createDb();
        server.start();
    }

    public static void stopServer() {
        server.stop(5);
    }

}
