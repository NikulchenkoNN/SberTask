package ru.task.bankAPI.services;

import java.util.*;

public class CardNumber {
    private static CardNumber cardNumber;
    protected static Set<String> cards;

    private CardNumber() {
        cards = new HashSet<>();
    }

    public static CardNumber getInstance() {
        if (cardNumber == null)
            cardNumber = new CardNumber();
        return cardNumber;
    }

    public static String createNumber() {
        StringBuilder result = new StringBuilder();
        if (cards.isEmpty())
        {
            generateNumber(result);
        } else {
            int cardsSize = cards.size();
            while (cards.size() < cardsSize+1) {
                generateNumber(result);
            }
        }
        return takeCardNumber();
    }

    private static void generateNumber(StringBuilder result) {
        for (int i = 0; i < 4; i++) {
            int res = (int) (Math.random() * 9000) + 1000;
            result.append(res);
        }
        cards.add(result.toString());
    }

    private static String takeCardNumber() {
        List<String> stringList = new ArrayList<>(cards);
        return stringList.get(stringList.size()-1);
    }
}
