package ru.task.bankAPI.service;

import ru.task.bankAPI.connection.DataSourceHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserCardService {
    public static void addCardToUser(Long userId, String cardNumber) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("update CARD set BANK_USER_ID = ? where NUMBER = ?")) {
            statement.setLong(1, userId);
            statement.setString(2, cardNumber);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
