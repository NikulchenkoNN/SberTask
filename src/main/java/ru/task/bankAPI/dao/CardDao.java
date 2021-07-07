package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.Card;

import java.math.BigDecimal;
import java.util.Set;

public interface CardDao {
    Card createCard(Card card);
    Card findCardById(Long userId);
    Set<Card> getCardsByUser(Long userId);
    BigDecimal getCardBalance(Long userId, Long cardId);
    void updateCardBalance(Long userId, Long cardId, BigDecimal cash);
}
