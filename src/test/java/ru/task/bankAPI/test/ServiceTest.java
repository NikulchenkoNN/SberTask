package ru.task.bankAPI.test;

import jdk.internal.org.objectweb.asm.commons.StaticInitMerger;
import org.h2.tools.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class ServiceTest {
    private static final CardNumber cardNumber = CardNumber.getInstance();
    private static Service service = new ServiceImpl();

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
        User user1 = service.createUser("Nick");
        User user2 = service.createUser("Alex");
        User user3 = service.createUser("Dim");

        User nick = service.findUserByName("Nick");
        User alex = service.findUserByName("Alex");
        User dim = service.findUserByName("Dim");

        Assertions.assertEquals(user1, nick);
        Assertions.assertEquals(user2, alex);
        Assertions.assertEquals(user3, dim);
    }

    @Test
    public void createCardsTest() {
        String num1 = CardNumber.createNumber();
        String num2 = CardNumber.createNumber();
        String num3 = CardNumber.createNumber();

        service.createCard(num1);
        service.createCard(num2);
        service.createCard(num3);

        User user = service.createUser("Default");

        UserCardService.addCardToUser(service.findUserByName(user.getName()).getId(), num1);
        UserCardService.addCardToUser(service.findUserByName(user.getName()).getId(), num2);
        UserCardService.addCardToUser(service.findUserByName(user.getName()).getId(), num3);

        Set<Card> cards = service.getCardsByUser(user.getId());
        cards.forEach(System.out::println);
        Assertions.assertEquals(3, cards.size());
    }

    @Test
    public void addCardToUserTest() {
        String card1Number = CardNumber.createNumber();
        String card2Number = CardNumber.createNumber();
        String card3Number = CardNumber.createNumber();

        service.createCard(card1Number);
        service.createCard(card2Number);
        service.createCard(card3Number);

        User user1 = service.createUser("Nick");
        User user2 = service.createUser("Alex");

        service.addCardToUser(user1.getId(), card1Number);
        service.addCardToUser(user1.getId(), card2Number);
        service.addCardToUser(user2.getId(), card3Number);

        Set<Card> nickCards = service.getCardsByUser(1L);
        nickCards.forEach(System.out::println);
        Assertions.assertEquals(2, nickCards.size());
        System.out.println("------");

        Set<Card> alexCards = service.getCardsByUser(2L);
        alexCards.forEach(System.out::println);
        Assertions.assertEquals(1, alexCards.size());
    }

    @Test
    public void cardGetCardBalanceTest() {
        String card1Number = CardNumber.createNumber();

        User user1 = service.createUser("Alex");

        service.createCard(card1Number);

        service.addCardToUser(user1.getId(), card1Number);

        Set<Card> cards = service.getCardsByUser(user1.getId());
        for (Card card : cards) {
            System.out.println(service.getBalance(user1.getId(), card.getId()));
        }
    }

    @Test
    public void updateBalanceTest() {
        String card1Number = CardNumber.createNumber();

        User user = service.createUser("Vlad");

        service.createCard(card1Number);

        service.addCardToUser(user.getId(), card1Number);

        BigDecimal beforeUpdate = service.getBalance(user.getId(), service.findCardByUserId(user.getId()).getId());
        service.updateBalance(user.getId(), service.findCardByUserId(user.getId()).getId(), BigDecimal.valueOf(12.5));
        BigDecimal afterUpdate = service.getBalance(user.getId(), service.findCardByUserId(user.getId()).getId());
        Assertions.assertNotEquals(beforeUpdate, afterUpdate);
    }

    @Test
    public void Test() {
        User user1 = service.createUser("Alex");
        User user2 = service.createUser("Alex");
        Assertions.assertEquals(1, service.getAllUsers().size());
        User user3 = service.createUser("Mike");

        String num1 = CardNumber.createNumber();
        service.createCard(num1);

        String num2 = CardNumber.createNumber();
        service.createCard(num2);

//        String num3 = CardNumber.createNumber();
//        service.createCard(num3);
//
//        String num4 = CardNumber.createNumber();
//        service.createCard(num3);

        service.addCardToUser(user1.getId(), num1);
        service.addCardToUser(user2.getId(), num2);
//        service.addCardToUser(user3.getId(), num3);
//        service.addCardToUser(user3.getId(), num4);


    }
}