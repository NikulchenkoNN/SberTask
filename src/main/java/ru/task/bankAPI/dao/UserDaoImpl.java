package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserDaoImpl implements UserDao {

    @Override
    public User findUserById(int userId) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u where u.=?")) {
            statement.setInt(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
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
            throw new RuntimeException("Create user failure");
        }
    }

    @Override
    public User findUserByName(String userName) {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u where u.name=?")) {
            statement.setString(1, userName);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("tuta");
        }
    }

    @Override
    public Set<User> getUsers() {
        try (PreparedStatement statement = DataSourceHelper.connection()
                .prepareStatement("select * from user u left join card c on u.id=c.BANK_USER_ID")) {
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
            throw new RuntimeException();
        }
    }

    private User resultSetForUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("ID"));
        user.setName(resultSet.getString("NAME"));
        try {
            int cardId = resultSet.getInt("CARD_ID");
            if (cardId != 0) {
                String cardNumber = resultSet.getString("NUMBER");
                Card card = new Card();
                card.setId(cardId);
                card.setNumber(cardNumber);
                card.setUser(user);
                user.getCards().add(card);
            }
        } catch (Exception e) {
        }
        return user;
    }
}
