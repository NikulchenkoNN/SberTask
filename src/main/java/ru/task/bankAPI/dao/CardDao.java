package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.util.Set;

public interface CardDao {
    Card createCard(String number);
    Set<Card> getCardsByUser(String userName);
    double getBalanceCard(User user, String cardNumber);
    void updateBalance(User user, String cardNumber, double cash);
}
