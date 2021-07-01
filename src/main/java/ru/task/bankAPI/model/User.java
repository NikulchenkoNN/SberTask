package ru.task.bankAPI.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
     private int id;
     private String name;
     private List<Card> cards;
}
