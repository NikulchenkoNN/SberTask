package ru.task.bankAPI.cardnumber;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CardNumber {
    private static CardNumber cardNumber;
    protected static Set<String> cards;

    private CardNumber() {
        cards = new LinkedHashSet<>();
    }

    public static CardNumber getInstance() {
        if (cardNumber == null)
            cardNumber = new CardNumber();
        return cardNumber;
    }

    public String createNumber() {
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

    private void generateNumber(StringBuilder result) {
        for (int i = 0; i < 4; i++) {
            int res = (int) (Math.random() * 9000) + 1000;
            result.append(res);
        }
        cards.add(result.toString());
    }

    public String takeCardNumber() {
        List<String> stringList = new ArrayList<>(cards);
        return stringList.get(stringList.size()-1);
    }
}
