package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDaoImpl implements UserDao {

    @Override
    public User createUser(User user) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("insert into user (name) values (?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from USER left join CARD C on USER.ID = C.BANK_USER_ID where USER.ID = ?")) {
            statement.setLong(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByName(String userName) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from USER left join CARD C on USER.ID = C.BANK_USER_ID where NAME = ?")) {
            statement.setString(1, userName);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<User> getUsers() {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u left join CARD c on u.ID=c.BANK_USER_ID")) {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            Set<User> users = new HashSet<>();
            while (resultSet.next()) {
                User user = resultSetForUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User resultSetForUser(ResultSet resultSet) throws SQLException {
        User user = new User();

        try {
//            user.setCards(new ArrayList<>());
            user.setId(resultSet.getLong("ID"));
            user.setName(resultSet.getString("NAME"));
        } catch (SQLException e) {
            return null;
        }
        try {
            do {
                Object cardId = resultSet.getObject(3);
                if (cardId != null) {
                    String cardNumber = resultSet.getString("NUMBER");
                    BigDecimal cardBalance = resultSet.getBigDecimal("BALANCE");
                    Long bankUserId = resultSet.getLong("BANK_USER_ID");
                    if (user.getId().equals(bankUserId)) {
                        Card card = new Card();
                        card.setId(Long.parseLong(cardId.toString()));
                        card.setNumber(cardNumber);
                        card.setBalance(cardBalance);
                        card.setUser(user);
                        user.getCards().add(card);
                    }
                }
            } while (resultSet.next());
        } catch (SQLException ignored) {
        }
        return user;
    }
}
