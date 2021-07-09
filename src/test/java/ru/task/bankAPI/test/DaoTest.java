package ru.task.bankAPI.test;

import org.h2.tools.Server;
import org.junit.jupiter.api.*;
import ru.task.bankAPI.connection.DataSourceHelper;
import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardNumber;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaoTest {
    private static UserDao userDao;
    private static CardDao cardDao;
    static String num1;

    @BeforeAll
    public static void createDb() throws SQLException {
        DataSourceHelper.createDb();
        Server.createTcpServer().start();
        Statement statement = DataSourceHelper.connection().createStatement();
        statement.executeUpdate("insert into USER (NAME) values ('Alex')");
        CardNumber cardNumber = CardNumber.getInstance();
        num1 = CardNumber.createNumber();
        statement.executeUpdate("insert into CARD (NUMBER, BALANCE, BANK_USER_ID) values (" + num1 + ", 15.00, 1)");
        userDao = new UserDaoImpl();
        cardDao = new CardDaoImpl();
    }

    @Test
    public void getUser() {
        User actual = userDao.findUserByName("Alex");

        User expected = new User();
        expected.setId(1L);
        expected.setName("Alex");

        Card card = new Card();
        card.setId(1L);
        card.setNumber(num1);
        card.setBalance(BigDecimal.valueOf(15.00));
        card.setUser(expected);

        List<Card> cards = new ArrayList<>();
        cards.add(card);

        expected.setCards(cards);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findUser(){
        User actual = userDao.findUserById(1L);

        User expected = new User();
        expected.setId(1L);
        expected.setName("Alex");

        Card card = new Card();
        card.setId(1L);
        card.setNumber(num1);
        card.setBalance(BigDecimal.valueOf(15.00));
        card.setUser(expected);
        List<Card> cards = new ArrayList<>();
        cards.add(card);

        expected.setCards(cards);

        Assertions.assertEquals(expected, actual);
        actual = userDao.findUserByName("Alex");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getCard() {
        Set<Card> actual = cardDao.getCardsByUser(1L);
        User user = new User();
        user.setName("Alex");
        Card card = new Card();
        card.setId(1L);
        card.setNumber(num1);
        card.setBalance(BigDecimal.valueOf(15.00));
        card.setUser(user);
        Set<Card> expected = new HashSet<>();
        expected.add(card);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getBalance() {
        BigDecimal actual = cardDao.getCardBalance(1L, 1L);
        Assertions.assertEquals("15.00", actual.toString());
    }

    @Test
    public void updateBalance() {
        cardDao.updateCardBalance(1L, 1L, BigDecimal.valueOf(12.34));
        BigDecimal actual = cardDao.getCardBalance(1L, 1L);
        BigDecimal expected = BigDecimal.valueOf(15.00).add(BigDecimal.valueOf(12.34));
        Assertions.assertEquals(expected, actual);
    }
}
