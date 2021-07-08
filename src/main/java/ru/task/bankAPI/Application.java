package ru.task.bankAPI;

import ru.task.bankAPI.server.MyServer;

import java.io.IOException;


public class Application {
    public static void main(String[] args) throws IOException {
        MyServer.startServer();
    }
}
