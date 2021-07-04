package ru.task.bankAPI.dao;

import org.h2.tools.Server;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.task.bankAPI.cardnumber.CardNumber;
import ru.task.bankAPI.connection.DataSourceHelper;

import java.sql.SQLException;

public class StatementsTest {
    private static UserDao userDao;
    private static CardDao cardDao;

    @BeforeAll
    public static void createDao() throws SQLException {
        DataSourceHelper.createDB();
        userDao = new UserDaoImpl();
        cardDao = new CardDaoImpl(userDao);
        Server.createTcpServer().start();
    }

    @Test
    public void createCard() {
        CardNumber cardNumber = CardNumber.getInstance();

        System.out.println(cardNumber.createNumber());


    }
}