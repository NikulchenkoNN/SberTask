package ru.task.bankAPI.service;

import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.model.Card;

import java.math.BigDecimal;
import java.util.Set;

public class CardService {
    static CardDao cardDao = new CardDaoImpl();

    public static void createCard(String number) {
        Card card = new Card();
        card.setNumber(number);
        cardDao.createCard(card);
    }

    public static Set<Card> getCardsByUser(Long userId) {
        return cardDao.getCardsByUser(userId);
    }

    public static void updateBalance(Long userId, Long cardId, BigDecimal cash) {
        cardDao.updateCardBalance(userId, cardId, cash);
    }

    public static BigDecimal getBalance(Long userId, Long cardId) {
        return cardDao.getCardBalance(userId, cardId);
    }

    public static Card findCardById(Long userId) {
        return cardDao.findCardById(userId);
    }
}
