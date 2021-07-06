package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDaoImpl implements UserDao {

    @Override
    public User findUserById(int userId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u where u.ID= ?")) {
            statement.setInt(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Search by ID failure");
        }
    }

    @Override
    public User createUser(String userName) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("insert into user (name) values (?)")) {
            statement.setString(1, userName);
            statement.execute();
            return findUserByName(userName);
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
            throw new RuntimeException("username");
        }
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
                Optional<User> anyUser = users.stream().filter(user::equals).findAny();

                if (anyUser.isPresent()) {
                    anyUser.get().getCards().addAll(user.getCards());
                } else {
                    users.add(user);
                }
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
        user.setId(resultSet.getInt("ID"));
        user.setName(resultSet.getString("NAME"));
        try {
            while (resultSet.next()) {
                int cardId = resultSet.getInt("CARD_ID");
                if (cardId != 0) {
                    String cardNumber = resultSet.getString("NUMBER");
                    double cardBalance = resultSet.getDouble("BALANCE");
                    Card card = new Card();
                    card.setId(cardId);
                    card.setNumber(cardNumber);
                    card.setBalance(cardBalance);
                    card.setUser(user);
                    user.getCards().add(card);
                }
            }
        } catch (Exception e) {
        }
        return user;
    }
}
