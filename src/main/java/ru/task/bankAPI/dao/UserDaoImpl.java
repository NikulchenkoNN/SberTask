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
    public User findUserById(Long userId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u where u.ID= ?")) {
            statement.setLong(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

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

//    @Override
//    public User findUserByName(String userName) {
//        try (PreparedStatement statement = DataSourceHelper.connection()
//                .prepareStatement("select * from USER left join CARD C on USER.ID = C.BANK_USER_ID where NAME = ?")) {
//            statement.setString(1, userName);
//            statement.execute();
//            ResultSet resultSet = statement.getResultSet();
//            resultSet.next();
//            return resultSetForUser(resultSet);
//        } catch (SQLException e) {
//            throw new RuntimeException("username");
//        }
//    }

    @Override
    public List<User> getUsers() {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u left join CARD c on u.ID=c.BANK_USER_ID")) {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(resultSetForUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User resultSetForUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setCards(new ArrayList<>());
        user.setId(resultSet.getLong("ID"));
        user.setName(resultSet.getString("NAME"));
        do {
            Object cardId = resultSet.getObject(3);
            if (cardId != null) {
                String cardNumber = resultSet.getString("NUMBER");
                BigDecimal cardBalance = resultSet.getBigDecimal("BALANCE");
                Card card = new Card();
                card.setId(Long.parseLong(cardId.toString()));
                card.setNumber(cardNumber);
                card.setBalance(cardBalance);
                card.setUser(user);
                if (card.getUser().getId() == user.getId()) {
                    user.getCards().add(card);
                }
            }
        } while (resultSet.next());
        return user;
    }
}
