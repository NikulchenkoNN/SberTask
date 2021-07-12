package ru.task.bankAPI.services;

import ru.task.bankAPI.model.Card;

import java.math.BigDecimal;
import java.util.Set;

public interface CardService {
    void addCardToUser(Long userId, String cardNumber);
    void createCard(String number);
    Set<Card> getCardsByUser(Long userId);
    void updateBalance(Long userId, Long cardId, BigDecimal cash);
    BigDecimal getBalance(Long userId, Long cardId);
    Card findCardByUserId(Long userId);
}
