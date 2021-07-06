package ru.task.bankAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@JsonAutoDetect
public class Card {
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String number;
    @Getter @Setter
    private BigDecimal balance;
    @JsonIgnore
    @Getter @Setter
    private User user;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", balance=" + balance +
                (user != null ? ", user={id:" + user.getId() + "}" : "") +
                '}';
    }
}
