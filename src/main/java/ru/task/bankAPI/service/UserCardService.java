package ru.task.bankAPI.service;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.UserDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserCardService {
    private static UserDao userDao;
    private static CardDao cardDao;
    public static void addCardToUser(String userName, String cardNumber) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("update CARD set BANK_USER_ID = ? where ID = ?")) {
            statement.setInt(1, userDao.findUserByName(userName).getId());
            statement.setInt(2, cardDao.findCardByNumber(cardNumber).getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
