package ru.task.bankAPI.test;

import org.h2.tools.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardNumber;
import ru.task.bankAPI.services.CardService;
import ru.task.bankAPI.services.CardServiceImpl;
import ru.task.bankAPI.services.UserServiceImpl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class ServiceTest {
    private static final CardNumber cardNumber = CardNumber.getInstance();
    private CardService cardService = CardServiceImpl.getInstance();
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @BeforeAll
    public static void createDao() throws SQLException {
        DataSourceHelper.createDb();
        Server.createTcpServer().start();
    }

    @BeforeEach
    public void clearDb() throws SQLException {
        Statement statement = DataSourceHelper.connection().createStatement();
        statement.execute("truncate table card");
        statement.execute("truncate table user");
    }

    @Test
    public void testConnectToServerTest() {
        try (Connection connection = DataSourceHelper.connection()) {
            Assertions.assertTrue(connection.isValid(1));
            Assertions.assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createdUsersCreateSameUserInBaseTest() {
        User user1 = userService.createUser("Nick");
        User user2 = userService.createUser("Alex");
        User user3 = userService.createUser("Dim");

        User nick = userService.findUserByName("Nick");
        User alex = userService.findUserByName("Alex");
        User dim = userService.findUserByName("Dim");

        Assertions.assertEquals(user1, nick);
        Assertions.assertEquals(user2, alex);
        Assertions.assertEquals(user3, dim);
    }

    @Test
    public void createCardsTest() {
        String num1 = CardNumber.createNumber();
        String num2 = CardNumber.createNumber();
        String num3 = CardNumber.createNumber();

        cardService.createCard(num1);
        cardService.createCard(num2);
        cardService.createCard(num3);

        User user = userService.createUser("Default");

        cardService.addCardToUser(userService.findUserByName(user.getName()).getId(), num1);
        cardService.addCardToUser(userService.findUserByName(user.getName()).getId(), num2);
        cardService.addCardToUser(userService.findUserByName(user.getName()).getId(), num3);

        Set<Card> cards = cardService.getCardsByUser(user.getId());
        cards.forEach(System.out::println);
        Assertions.assertEquals(3, cards.size());
    }

    @Test
    public void addCardToUserTest() {
        String card1Number = CardNumber.createNumber();
        String card2Number = CardNumber.createNumber();
        String card3Number = CardNumber.createNumber();

        cardService.createCard(card1Number);
        cardService.createCard(card2Number);
        cardService.createCard(card3Number);

        User user1 = userService.createUser("Nick");
        User user2 = userService.createUser("Alex");

        cardService.addCardToUser(user1.getId(), card1Number);
        cardService.addCardToUser(user1.getId(), card2Number);
        cardService.addCardToUser(user2.getId(), card3Number);

        Set<Card> nickCards = cardService.getCardsByUser(1L);
        nickCards.forEach(System.out::println);
        Assertions.assertEquals(2, nickCards.size());
        System.out.println("------");

        Set<Card> alexCards = cardService.getCardsByUser(2L);
        alexCards.forEach(System.out::println);
        Assertions.assertEquals(1, alexCards.size());
    }

    @Test
    public void cardGetCardBalanceTest() {
        String card1Number = CardNumber.createNumber();

        User user1 = userService.createUser("Alex");

        cardService.createCard(card1Number);

        cardService.addCardToUser(user1.getId(), card1Number);

        Set<Card> cards = cardService.getCardsByUser(user1.getId());
        for (Card card : cards) {
            System.out.println(cardService.getBalance(user1.getId(), card.getId()));
        }
    }

    @Test
    public void updateBalanceTest() {
        String card1Number = CardNumber.createNumber();

        User user = userService.createUser("Vlad");

        cardService.createCard(card1Number);

        cardService.addCardToUser(user.getId(), card1Number);

        BigDecimal beforeUpdate = cardService.getBalance(user.getId(), cardService.findCardByUserId(user.getId()).getId());
        cardService.updateBalance(user.getId(), cardService.findCardByUserId(user.getId()).getId(), BigDecimal.valueOf(12.5));
        BigDecimal afterUpdate = cardService.getBalance(user.getId(), cardService.findCardByUserId(user.getId()).getId());
        Assertions.assertNotEquals(beforeUpdate, afterUpdate);
    }

    @Test
    public void Test() {
        User user1 = userService.createUser("Alex");
        User user2 = userService.createUser("Alex");
        Assertions.assertEquals(1, userService.getAllUsers().size());
        User user3 = userService.createUser("Mike");

        String num1 = CardNumber.createNumber();
        cardService.createCard(num1);

        String num2 = CardNumber.createNumber();
        cardService.createCard(num2);

//        String num3 = CardNumber.createNumber();
//        service.createCard(num3);
//
//        String num4 = CardNumber.createNumber();
//        service.createCard(num3);

        cardService.addCardToUser(user1.getId(), num1);
        cardService.addCardToUser(user2.getId(), num2);
//        service.addCardToUser(user3.getId(), num3);
//        service.addCardToUser(user3.getId(), num4);


    }
}