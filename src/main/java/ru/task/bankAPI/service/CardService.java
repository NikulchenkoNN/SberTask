package ru.task.bankAPI.service;

import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.Card;

public class CardService {
    private UserDao userDao = new UserDaoImpl();
    private CardDao cardDao = new CardDaoImpl(userDao);

    public Card createCard(String number) {
        Card card = new Card();
        card.setNumber(number);
        cardDao.createCard(card);
        return card;
    }
}
