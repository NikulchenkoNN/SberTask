package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class CardDaoImpl implements CardDao {
    private UserDao userDao;

    public CardDaoImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Card createCard(String number) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("insert into card (number) values (?)")) {
            statement.setString(1, number);
            statement.execute();
            return findCardByNumber(number);
        } catch (SQLException e) {
            throw new RuntimeException("Create card failure");
        }
    }

    @Override
    public Card findCardByNumber(String number) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from card c where c.number = ?")) {
            statement.setString(1, number);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForCard(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("find dy card number");
        }
    }

    @Override
    public Set<Card> getCardsByUser(String userName) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from card c left join (select * form user where user.name = ?) u on u.id = c.user_id")) {
            statement.setString(1, userName);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            Set<Card> cards = new HashSet<>();
            while (resultSet.next()) {
                cards.add(resultSetForCard(resultSet));
            }
            return cards;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void updateCardBalance(String user, String cardNumber, double cash) {
        double oldBalance = getCardBalance(userDao.findUserByName(user).getName(), cardNumber);
        double newBalance = oldBalance + cash;
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("update card set balance = ? where number = ?")) {
            statement.setDouble(1, newBalance);
            statement.setString(2, cardNumber);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public double getCardBalance(String user, String cardNumber) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from card c inner join (select * from user where id = ?) u on u.id = c.bank_user_id where c.number = ?")) {
            statement.setInt(1, userDao.findUserByName(user).getId());
            statement.setString(2, cardNumber);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForCard(resultSet).getBalance();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Card resultSetForCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getInt("ID"));
        card.setNumber(resultSet.getString("NUMBER"));
        card.setBalance(resultSet.getDouble("BALANCE"));
        int userId = resultSet.getInt("BANK_USER_ID");
        if (userId != 0)
            card.setUser(userDao.findUserById(userId));
        return card;
    }
}
