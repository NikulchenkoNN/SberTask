package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CardDaoImpl implements CardDao {
    UserDao userDao = new UserDaoImpl();
    private static final String CREATE_CARD = "insert into CARD (number) values (?)";
    private static final String FIND_BY_USER_ID = "select * from CARD  where BANK_USER_ID = ?";
    private static final String GET_CARDS_BY_USER_ID = "select * from CARD c where c.BANK_USER_ID = ?";
    private static final String UPDATE_BALANCE = "update CARD set balance = ? where ID = ?";
    private static final String GET_BALANCE = "select * from CARD where BANK_USER_ID = ? and ID = ?";
    private static final String ADD_CARD_TO_USER = "update CARD set BANK_USER_ID = ? where NUMBER = ?";

    @Override
    public void addCardToUser(Long userId, String cardNumber) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(ADD_CARD_TO_USER)) {
            statement.setLong(1, userId);
            statement.setString(2, cardNumber);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException();
        }    }

    @Override
    public Card createCard(Card card) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(CREATE_CARD, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, card.getNumber());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Object id = generatedKeys.getObject("ID");
                    if (id != null) {
                        card.setId(Long.parseLong(id.toString()));
                    } else {
                        return null;
                    }
                } else {
                    throw new SQLException("Creating card failed, no ID obtained.");
                }
            }
            return card;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public Card findCardByUserId(Long userId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(FIND_BY_USER_ID)) {
            statement.setLong(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForCard(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Card> getCardsByUser(Long userId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(GET_CARDS_BY_USER_ID)) {
            statement.setLong(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            Set<Card> cards = new HashSet<>();
            while (resultSet.next()) {
                cards.add(resultSetForCard(resultSet));
            }
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateCardBalance(Long userId, Long cardId, BigDecimal cash) {
        BigDecimal oldBalance = getCardBalance(userId, cardId);
        BigDecimal newBalance = oldBalance.add(cash);
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(UPDATE_BALANCE)) {
            statement.setBigDecimal(1, newBalance);
            statement.setLong(2, cardId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BigDecimal getCardBalance(Long userId, Long cardId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement(GET_BALANCE)) {
            statement.setLong(1, userId);
            statement.setLong(2, cardId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSet.getBigDecimal("BALANCE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Card resultSetForCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getLong("ID"));
        card.setNumber(resultSet.getString("NUMBER"));
        card.setBalance(resultSet.getBigDecimal("BALANCE"));
        Object userId = resultSet.getObject("BANK_USER_ID");
        if (userId != null) {
            Long id = Long.parseLong(userId.toString());
            card.setUser(userDao.findUserById(id));
        }
        return card;
    }
}
