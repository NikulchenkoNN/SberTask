package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class CardDaoImpl implements CardDao {
    private UserDao userDao;

    @Override
    public Card createCard(String number) {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("insert into card (number) value ?")) {
            statement.setString(1, number);
            statement.execute();

            return findCard(number);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Card findCard(String number) {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("select * from card c where c.number = ?")) {
            statement.setString(1, number);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSetForCard(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Set<Card> getCardsByUser(String userName) {
        try (PreparedStatement statement = DataSourceHelper.createConnection()
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

    private Card resultSetForCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getInt(1));
        card.setNumber(resultSet.getString(2));
        int userId = resultSet.getInt(resultSet.getInt(2));
        if (userId != 0)
            card.setUser(userDao.findUserById(userId));

        return card;
    }
}
