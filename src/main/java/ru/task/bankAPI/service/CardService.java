package ru.task.bankAPI.service;

import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.model.Card;

import java.util.Set;

public class CardService {
    static CardDao cardDao = new CardDaoImpl();

    public static Card createCard(String number) {
        Card card = new Card();
        card.setNumber(number);
        cardDao.createCard(card);
        return card;
    }

    public static Set<Card> getCardsByUser(Long userId) {
        return cardDao.getCardsByUser(userId);
    }
}
