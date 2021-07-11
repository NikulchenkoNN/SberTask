package ru.task.bankAPI.services;

import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface Service {
    User createUser(String name);
    User findUserById(Long userId);
    User findUserByName(String userName);
    void createCard(String number);
    Card findCardByUserId(Long userId);
    Set<Card> getCardsByUser(Long userId);
    void updateBalance(Long userId, Long cardId, BigDecimal cash);
    BigDecimal getBalance(Long userId, Long cardId);
    void addCardToUser(Long userId, String cardNumber);
}
