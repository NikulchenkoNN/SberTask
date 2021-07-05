package ru.task.bankAPI.service;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserCardService {
    public static void addCardToUser(User user, Card card) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("update CARD set BANK_USER_ID = ? where ID = ?")) {
            statement.setInt(1, user.getId());
            statement.setInt(2, card.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


}
