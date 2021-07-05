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

     @Override
     public String toString() {
          return "User{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", cards=" + cards +
                  '}';
     }
}
