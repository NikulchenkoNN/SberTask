package ru.task.bankAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@JsonAutoDetect
public class Card {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String number;
    @Getter @Setter
    private BigDecimal balance;
    @Getter @Setter
    private User user;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                (user != null ? ", user={name:" + user.getName() + "}" : "") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number.equals(card.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
