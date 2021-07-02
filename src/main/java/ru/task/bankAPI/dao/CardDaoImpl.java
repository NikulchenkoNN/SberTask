package ru.task.bankAPI.dao;

import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class CardDaoImpl implements CardDao
{
    @Override
    public Card createCard(String number) {
        try (PreparedStatement statement = DataSourceHelper.createConnection().prepareStatement("insert into card (number) value ?")){
            statement.setString(1, number);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            return resultSetForCard(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Set<Card> getCardsByUser(String userName) {
return null;
    }

    @Override
    public Set<Card> getCards() {
        return null;
    }

    @Override
    public void setBalance(String userName, String number, Double balance) {

    }

    @Override
    public double getBalance(String userName, String number) {
        return 0;
    }

    private Card resultSetForCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setNumber(resultSet.getNString(1));
        try {
            int userId = resultSet.getInt(resultSet.getInt(2));
            if (userId != 0) {
                String userName = resultSet.getString(3);
                User user = new User();
                user.setId(userId);
                user.setName(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return card;
    }
}
