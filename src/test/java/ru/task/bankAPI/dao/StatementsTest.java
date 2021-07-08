package ru.task.bankAPI.dao;

import org.h2.tools.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.service.CardNumber;
import ru.task.bankAPI.service.CardService;
import ru.task.bankAPI.service.UserCardService;
import ru.task.bankAPI.service.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class StatementsTest {
    private static final CardNumber cardNumber = CardNumber.getInstance();

    @BeforeAll
    public static void createDao() throws SQLException {
        DataSourceHelper.createDb();
        Server.createTcpServer().start();
    }

    @Test
    public void createdUsersSameInBaseTest() {
        User user1 = UserService.createUser("Nick");
        User user2 = UserService.createUser("Alex");
        User user3 = UserService.createUser("Dim");

        User nick = UserService.findUserByName("Nick");
        User alex = UserService.findUserByName("Alex");
        User dim = UserService.findUserByName("Dim");

        Assertions.assertEquals(user1, nick);
        Assertions.assertEquals(user2, alex);
        Assertions.assertEquals(user3, dim);
    }

    @Test
    public void createCardTest() {
        String num1 = CardNumber.createNumber();
        String num2 = CardNumber.createNumber();
        String num3 = CardNumber.createNumber();

        CardService.createCard(num1);
        CardService.createCard(num2);
        CardService.createCard(num3);

        User user = UserService.createUser("Default");

        UserCardService.addCardToUser(UserService.findUserByName(user.getName()).getId(), num1);
        UserCardService.addCardToUser(UserService.findUserByName(user.getName()).getId(), num2);
        UserCardService.addCardToUser(UserService.findUserByName(user.getName()).getId(), num3);

        Set<Card> cards = CardService.getCardsByUser(user.getId());
        cards.forEach(System.out::println);
        Assertions.assertEquals(3, cards.size());
    }

    @Test
    public void addCardToUserTest() {
        String card1Number = CardNumber.createNumber();
        String card2Number = CardNumber.createNumber();
        String card3Number = CardNumber.createNumber();

        CardService.createCard(card1Number);
        CardService.createCard(card2Number);
        CardService.createCard(card3Number);

        User user1 = UserService.createUser("Nick");
        User user2 = UserService.createUser("Alex");

        UserCardService.addCardToUser(user1.getId(), card1Number);
        UserCardService.addCardToUser(user1.getId(), card2Number);
        UserCardService.addCardToUser(user2.getId(), card3Number);

        Set<Card> nickCards = CardService.getCardsByUser(1L);
        nickCards.forEach(System.out::println);
        Assertions.assertEquals(2, nickCards.size());
        System.out.println("------");

        Set<Card> alexCards = CardService.getCardsByUser(2L);
        alexCards.forEach(System.out::println);
        Assertions.assertEquals(1, alexCards.size());
    }

    @Test
    public void testConnectToServer() {
        try (Connection connection = DataSourceHelper.connection()) {
            Assertions.assertTrue(connection.isValid(1));
            Assertions.assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cardGetCardBalance() {
        String card1Number = CardNumber.createNumber();

        User user1 = UserService.createUser("Alex");

        CardService.createCard(card1Number);

        UserCardService.addCardToUser(user1.getId(), card1Number);

        Set<Card> cards = CardService.getCardsByUser(user1.getId());
        for (Card card: cards) {
            System.out.println(CardService.getBalance(user1.getId(), card.getId()));
        }
    }
}