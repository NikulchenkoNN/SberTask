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
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("select * from user u where u.id=?")) {
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
    public User findUserByName(String name) {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("select * from user u where u.name=?")) {
            statement.setString(1, name);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public User createUser(String name) {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("insert into user (name) value (?)")) {
            statement.setString(1, name);
            statement.execute();
            return findUserByName(name);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Set<User> getUsers() {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("select * from user u left join card c on u.id=c.user_id")) {
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

    @Override
    public double getBalanceCard(User user, String cardNumber) {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("select * from card where number = ? and bank_user_id = ?")) {

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return 0;
    }

    private User resultSetForUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        try {
            int cardId = resultSet.getInt(3);
            if (cardId != 0) {
                String cardNumber = resultSet.getString(4);
                double cardBalance = resultSet.getDouble(5);
                Card card = new Card();
                card.setId(cardId);
                card.setNumber(cardNumber);
                card.setUser(user);
                card.setBalance(cardBalance);
                user.getCards().add(card);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
