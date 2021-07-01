package ru.task.bankAPI.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
    static final String DB_USERNAME = "root";
    static final String DB_PASSWORD = "root";
    static final String DB_URL = "jdbc:mysql://localhost:3306";
    static final String DROP_DB = "DROP DATABASE [IF EXISTS] USERS";
    static final String CREATE_DB = "CREATE DATABASE USERS";

    public static void createDB() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement())
        {
            statement.executeUpdate(DROP_DB);
            statement.executeUpdate(CREATE_DB);
        } catch (SQLException throwables) {
            System.out.println("Create or delete DB failure");
            throwables.printStackTrace();
        }
    }

    public static Connection createCon() {
        Connection connection = null;
        try {
            connection= DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException throwables) {
            System.out.println("Connection create false");
            throwables.printStackTrace();
        }
        return connection;
    }
}