package ru.task.bankAPI;

import ru.task.bankAPI.server.Server;

import java.io.IOException;


public class Application {
    public static void main(String[] args) throws IOException {
        Server.startServer();
    }
}
