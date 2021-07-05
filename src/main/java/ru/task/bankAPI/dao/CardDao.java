package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.Card;

import java.util.Set;

public interface CardDao {
    Card createCard(String number);
    Set<Card> getCardsByUser(String userName);
    double getBalanceCard(int userID, String cardNumber);
    void updateBalance(int userID, String cardNumber, int cash);
}
