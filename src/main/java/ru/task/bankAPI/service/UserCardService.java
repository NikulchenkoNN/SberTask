package ru.task.bankAPI.service;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserCardService {
    private static UserDao userDao = new UserDaoImpl();
    private static CardDao cardDao = new CardDaoImpl(userDao);
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
