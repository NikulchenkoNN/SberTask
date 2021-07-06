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
import java.util.Set;

import static ru.task.bankAPI.service.UserCardService.addCardToUser;

public class StatementsTest {
    private static UserDao userDao;
    private static CardDao cardDao;
    private static final CardNumber cardNumber = CardNumber.getInstance();


    @BeforeAll
    public static void createDao() throws SQLException {
        DataSourceHelper.createDb();
        userDao = new UserDaoImpl();
        cardDao = new CardDaoImpl(userDao);
        Server.createTcpServer().start();
        System.out.println();
    }

//    @BeforeEach
//    public void clearDB() {
//        try (Statement statement = DataSourceHelper.connection().createStatement()) {
//            statement.executeUpdate("truncate table card");
//            statement.executeUpdate("truncate table user");
//        } catch (SQLException e) {
//            throw new RuntimeException();
//        }
//    }

    @Test
    public void createEntities() {
        User user1 = userDao.createUser("Nick");
        User user2 = userDao.createUser("Dim");
        User user3 = userDao.createUser("Alex");

        String cardN1 = cardNumber.createNumber();
        Card card1 = cardDao.createCard(cardN1);

        String cardN2 = cardNumber.createNumber();
        Card card2 = cardDao.createCard(cardN2);

        String cardN3 = cardNumber.createNumber();
        Card card3 = cardDao.createCard(cardN3);

        String cardN4 = cardNumber.createNumber();
        Card card4 = cardDao.createCard(cardN4);

        String cardN5 = cardNumber.createNumber();
        Card card5 = cardDao.createCard(cardN5);

        addCardToUser("Nick", cardN1);
        addCardToUser("Nick", cardN2);
        addCardToUser("Dim", cardN3);
        addCardToUser("Alex", cardN4);
        addCardToUser("Dim", cardN5);
//
//        List<User> userSet = new ArrayList<>();
//        userSet.add(user1);
//        userSet.add(user2);
//        userSet.add(user3);
//
//        List<Card> cardSet = new ArrayList<>();
//        cardSet.add(card1);
//        cardSet.add(card2);
//        cardSet.add(card3);
//        cardSet.add(card4);
//        cardSet.add(card5);
//
//        System.out.println("Users");
//        userSet.forEach(System.out::println);
//        System.out.println();
//
//        System.out.println("Cards");
//        cardSet.forEach(System.out::println);
//        System.out.println();

        Set<User> users = userDao.getUsers();
        users.forEach(System.out::println);
    }

    @Test
    public void testConnect() {
        try (Connection connection = DataSourceHelper.connection()) {
            System.out.println(connection.isValid(1));
            System.out.println(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateBalance() {
        User alex = userDao.createUser("Alex");
        Card card = cardDao.createCard(cardNumber.createNumber());
        System.out.println(userDao.findUserByName("Alex"));
        addCardToUser(userDao.findUserByName("Alex").getName(), cardDao.findCardByNumber(card.getNumber()).getNumber());
        System.out.println();
        System.out.println(cardDao.getCardBalance(alex.getName(), card.getNumber()));
        cardDao.updateCardBalance(alex.getName(), card.getNumber(), 130);
        System.out.println(userDao.findUserByName("Alex"));
        System.out.println(cardDao.getCardBalance(alex.getName(), card.getNumber()));
    }
}