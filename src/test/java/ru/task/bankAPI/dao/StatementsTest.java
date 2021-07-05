package ru.task.bankAPI.dao;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.bankAPI.cardnumber.CardNumber;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.task.bankAPI.service.UserCardService.addCardToUser;

public class StatementsTest {
    private static UserDao userDao;
    private static CardDao cardDao;

    @BeforeAll
    public static void createDao() throws SQLException {
        DataSourceHelper.createDb();
        userDao = new UserDaoImpl();
        cardDao = new CardDaoImpl(userDao);
        Server.createTcpServer().start();
        System.out.println();
    }

    @BeforeEach
    public void clearDB() {
        try (Statement statement = DataSourceHelper.connection().createStatement()) {
            statement.executeUpdate("truncate table card");
            statement.executeUpdate("truncate table user");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void createEntities() {
        CardNumber cardNumber = CardNumber.getInstance();
        User user1 = userDao.createUser("Nick");
//        User user2 = userDao.createUser("Dima");
//        User user3 = userDao.createUser("Alex");

        Card card1 = cardDao.createCard(cardNumber.createNumber());
//        Card card2 = cardDao.createCard(cardNumber.createNumber());
//        Card card3 = cardDao.createCard(cardNumber.createNumber());
//        Card card4 = cardDao.createCard(cardNumber.createNumber());
//        Card card5 = cardDao.createCard(cardNumber.createNumber());



        addCardToUser(user1, card1);
//        addCardToUser(user1, card2);
//        addCardToUser(user2, card3);
//        addCardToUser(user3, card4);
//        addCardToUser(user2, card5);

        System.out.println(cardDao.getBalanceCard(user1, card1.getNumber()));

        cardDao.updateBalance(user1, card1.getNumber(), 15.12);

        System.out.println(cardDao.getBalanceCard(user1, card1.getNumber()));

        List<User> userSet = new ArrayList<>();
        userSet.add(user1);
//        userSet.add(user2);
//        userSet.add(user3);


        List<Card> cardSet = new ArrayList<>();
        cardSet.add(card1);
//        cardSet.add(card2);
//        cardSet.add(card3);
//        cardSet.add(card4);
//        cardSet.add(card5);

        System.out.println("Users");
        userSet.forEach(System.out::println);
        System.out.println();


        System.out.println("Cards");
        cardSet.forEach(System.out::println);
        System.out.println();
    }

    @Test
    public void connect() {
        try (Connection connection = DataSourceHelper.connection()) {
            System.out.println(connection.isValid(1));
            System.out.println(connection.isClosed());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}