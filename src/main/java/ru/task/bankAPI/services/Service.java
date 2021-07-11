package ru.task.bankAPI.services;

import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.math.BigDecimal;
import java.util.Set;

public interface Service {
    void addCardToUser(Long userId, String cardNumber);
    User createUser(String name);
    User findUserById(Long userId);
    User findUserByName(String userName);
    Set<User> getAllUsers();
    void createCard(String number);
    Set<Card> getCardsByUser(Long userId);
    Card findCardByUserId(Long userId);
    void updateBalance(Long userId, Long cardId, BigDecimal cash);
    BigDecimal getBalance(Long userId, Long cardId);
}
