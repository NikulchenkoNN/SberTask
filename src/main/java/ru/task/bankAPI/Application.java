package ru.task.bankAPI;

import com.sun.net.httpserver.HttpServer;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.httphandler.MyHttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.sql.Statement;


public class Application {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.setExecutor(null);
        DataSourceHelper.createDb();
//        clearDB();
        server.createContext("/show.users", new MyHttpHandler());
        server.createContext("/create.card", new MyHttpHandler());
        server.createContext("/create.user", new MyHttpHandler());
        server.createContext("/update.balance", new MyHttpHandler());

        server.start();

    }

    private static void clearDB() {
        try (Statement statement = DataSourceHelper.connection().createStatement()) {
            statement.executeUpdate("truncate table card");
            statement.executeUpdate("truncate table user");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
