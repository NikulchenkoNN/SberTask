package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.Card;

import java.util.Set;

public interface CardDao {
    Card createCard(String number);
    Set<Card> getCardsByUser(String userName);
    Set<Card> getCards();
    void setBalance(String userName, String number, Double balance);
    double getBalance(String userName, String number);
}
