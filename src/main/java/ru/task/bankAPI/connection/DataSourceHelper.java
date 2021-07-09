package ru.task.bankAPI.connection;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSourceHelper {

    public static Connection connection() throws SQLException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        connection.setAutoCommit(true);
        return connection;
    }

    public static void createDb() {
//        String sql = "set schema public;\n" +
//                "\n" +
//                "drop table IF EXISTS CARD;\n" +
//                "drop table IF EXISTS USER;\n" +
//                "\n" +
//                "create table USER\n" +
//                "(\n" +
//                "    id   BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
//                "    name VARCHAR(100)                      NOT NULL\n" +
//                ");\n" +
//                "CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_USER ON USER (name);\n" +
//                "\n" +
//                "create table CARD\n" +
//                "(\n" +
//                "    id           BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,\n" +
//                "    number       VARCHAR(16)                       NOT NULL,\n" +
//                "    balance      DECIMAL(20, 2) DEFAULT 0.0        NOT NULL,\n" +
//                "    BANK_USER_ID BIGINT,\n" +
//                "    foreign key (BANK_USER_ID) references USER (id)\n" +
//                ");\n" +
//                "CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_CARD ON CARD (number);";
        String sql;
            try {
                sql = FileUtils.readFileToString(new File(
                                DataSourceHelper.class.getResource("/database.sql").getFile()),
                        Charset.defaultCharset() );
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }

        try (PreparedStatement statement = DataSourceHelper.connection().prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}