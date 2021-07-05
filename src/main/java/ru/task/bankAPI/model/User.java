package ru.task.bankAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonAutoDetect
public class User {
     @Getter @Setter
     private int id;
     @Getter @Setter
     private String name;
     @Getter @Setter
     private List<Card> cards;

     public void addCard(Card card) {
          cards.add(card);
     }

     @Override
     public String toString() {
          return "User{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  (!cards.isEmpty() ? ", cards =" + cards : "") +
                  '}';
     }
}
