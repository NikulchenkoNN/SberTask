package ru.task.bankAPI.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@JsonAutoDetect
public class User {
     @Getter @Setter
     private Long id;
     @Getter @Setter
     private String name;
     @Getter @Setter
     private List<Card> cards;

     @Override
     public String toString() {
          return "User{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  (!cards.isEmpty() ? ", cards=" + cards : "") +
                  '}';
     }

     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          User user = (User) o;
          return id.equals(user.id) && name.equals(user.name) && Objects.equals(cards, user.cards);
     }

     @Override
     public int hashCode() {
          return Objects.hash(id, name, cards);
     }
}
