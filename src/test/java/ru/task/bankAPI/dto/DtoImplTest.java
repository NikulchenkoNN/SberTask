package ru.task.bankAPI.dto;

import org.junit.Test;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DtoImplTest {


    @Test
    public void fromDbToString() throws IOException {
        User user = new User();
        Card card = new Card();
        card.setId(1);
        card.setNumber("1234 2345 3456 6789");
        card.setUser(user);
        card.setBalance(15.01);
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        user.setId(1);
        user.setName("Nick");
        user.setCards(cards);
        System.out.println(new DtoImpl().fromJSONToString(user));
    }
}