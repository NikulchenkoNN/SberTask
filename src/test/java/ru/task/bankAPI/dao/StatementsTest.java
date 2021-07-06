package ru.task.bankAPI.dao;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.bankAPI.cardnumber.CardNumber;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.service.CardService;
import ru.task.bankAPI.service.UserCardService;
import ru.task.bankAPI.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

    @BeforeEach
    public void clearDB() {
        try (Statement statement = DataSourceHelper.connection().createStatement()) {
            statement.executeUpdate("truncate table USER");
            statement.executeUpdate("truncate table CARD");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Test
    public void createEntities() {
        UserService userService = new UserService();
        CardService cardService = new CardService();
        UserCardService service = new UserCardService();
        User user1 = userService.createUser("Nick");
        User user2 = userService.createUser("Alex");
        User user3 = userService.createUser("Dim");

        Card card1 = cardService.createCard(cardNumber.createNumber());
        Card card2 = cardService.createCard(cardNumber.createNumber());
        Card card3 = cardService.createCard(cardNumber.createNumber());
        Card card4 = cardService.createCard(cardNumber.createNumber());

        addCardToUser(user1.getId(), card1.getNumber());
        addCardToUser(user2.getId(), card2.getNumber());
        addCardToUser(user3.getId(), card3.getNumber());
        addCardToUser(user1.getId(), card4.getNumber());


//
//        addCardToUser("Nick", cardN1);
//        addCardToUser("Nick", cardN2);
//        addCardToUser("Dim", cardN3);
//        addCardToUser("Alex", cardN4);
//        addCardToUser("Dim", cardN5);


        List<User> users = userDao.getUsers();
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
    public void testDouble() {
        System.out.println(0.1+0.2);
    }

//    @Test
//    public void updateBalance() {
//        User alex = userDao.createUser("Alex");
//        Card card = cardDao.createCard(cardNumber.createNumber());
//        System.out.println(userDao.findUserByName("Alex"));
//        addCardToUser(userDao.findUserByName("Alex").getName(), cardDao.findCardByNumber(card.getNumber()).getNumber());
//        System.out.println();
//        System.out.println(cardDao.getCardBalance(alex.getName(), card.getNumber()));
//        cardDao.updateCardBalance(alex.getName(), card.getNumber(), 130);
//        System.out.println(userDao.findUserByName("Alex"));
//        System.out.println(cardDao.getCardBalance(alex.getName(), card.getNumber()));
//    }
}