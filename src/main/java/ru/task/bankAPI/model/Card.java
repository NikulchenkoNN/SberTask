package ru.task.bankAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
public class Card {
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String number;
    @Getter @Setter
    private double balance;
    @JsonIgnore
    @Getter @Setter
    private User user;

    @Override
    public String toString() {
        return "Card{" + "\n" +
                "id=" + id +",\n" +
                "umber='" + number + '\'' +",\n" +
                "balance=" + balance +"\n" +
                '}';
    }
}
