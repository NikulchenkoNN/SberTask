package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.Card;

import java.util.Set;

public interface CardDao {
    Card createCard(String number);
    Card findCardByNumber(String number);
    Set<Card> getCardsByUser(String userName);
    double getCardBalance(String user, String cardNumber);
    void updateCardBalance(String user, String cardNumber, double cash);
}
