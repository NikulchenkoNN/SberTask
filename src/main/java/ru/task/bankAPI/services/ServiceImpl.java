package ru.task.bankAPI.services;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class ServiceImpl implements Service {
    UserDao userDao = new UserDaoImpl();
    CardDao cardDao = new CardDaoImpl();
    private static final String ADD_CARD_TO_USER = "update CARD set BANK_USER_ID = ? where NUMBER = ?";

    @Override
    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        user = userDao.createUser(user);
        return user;
    }

    @Override
    public User findUserById(Long userId) {
        return userDao.findUserById(userId);
    }

    @Override
    public User findUserByName(String userName) {
        return userDao.findUserByName(userName);
    }

    @Override
    public void createCard(String number) {
        Card card = new Card();
        card.setNumber(number);
        cardDao.createCard(card);
    }

    @Override
    public Card findCardByUserId(Long userId) {
        return cardDao.findCardByUserId(userId);
    }

    @Override
    public Set<Card> getCardsByUser(Long userId) {
        return cardDao.getCardsByUser(userId);
    }

    @Override
    public void updateBalance(Long userId, Long cardId, BigDecimal cash) {
        cardDao.updateCardBalance(userId, cardId, cash);
    }

    @Override
    public BigDecimal getBalance(Long userId, Long cardId) {
        return cardDao.getCardBalance(userId, cardId);
    }

    @Override
    public void addCardToUser(Long userId, String cardNumber) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(ADD_CARD_TO_USER)) {
            statement.setLong(1, userId);
            statement.setString(2, cardNumber);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
