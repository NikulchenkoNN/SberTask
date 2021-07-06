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
