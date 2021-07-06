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
            final Connection connection = DriverManager.getConnection("jdbc:h2:mem:default", "sa", "");
            connection.setAutoCommit(true);
            return connection;
        }

        public static void createDb() {
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