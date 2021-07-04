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

    public static void createDB() {
        String sql = null;
        try {
            sql = FileUtils.readFileToString(new File(DataSourceHelper.class.getResource("/database.sql").getFile()), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException {
        final Connection connection = DriverManager.getConnection("jdbc:h2:mem:default", "sa", "");
        connection.setAutoCommit(true);
        return connection;
    }
}