package ru.task.bankAPI.services;

import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.model.Card;

import java.math.BigDecimal;
import java.util.Set;

public class CardServiceImpl implements CardService {
    private static CardServiceImpl cardService;
    static CardDao cardDao = new CardDaoImpl();

    private CardServiceImpl() {
    }

    @Override
    public void addCardToUser(Long userId, String cardNumber) {
        cardDao.addCardToUser(userId, cardNumber);
    }

    public static CardServiceImpl getInstance() {
        if (cardService == null) {
            return new CardServiceImpl();
        } else return cardService;
    }

    @Override
    public void createCard(String number) {
        Card card = new Card();
        card.setNumber(number);
        cardDao.createCard(card);
    }

    @Override
    public Set<Card> getCardsByUser(Long userId) {
        return cardDao.getCardsByUser(userId);
    }

    @Override
    public void updateBalance(Long userId, Long cardId, BigDecimal cash) {
        cardDao.updateCardBalance(userId, cardId, cash);
    }

    @Override
    public BigDecimal getBalance(Long userId, Long cardId) {
        return cardDao.getCardBalance(userId, cardId);
    }

    @Override
    public Card findCardByUserId(Long userId) {
        return cardDao.findCardByUserId(userId);
    }
}
