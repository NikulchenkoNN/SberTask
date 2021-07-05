package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.util.Set;

public interface CardDao {
    Card createCard(String number);
    Set<Card> getCardsByUser(String userName);
    double getBalanceCard(String user, String cardNumber);
    void updateCardBalance(String user, String cardNumber, double cash);
}
