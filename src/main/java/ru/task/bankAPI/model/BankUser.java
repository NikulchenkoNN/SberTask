package ru.task.bankAPI.model;

import lombok.Data;

import java.util.List;

@Data
public class BankUser {
     private int id;
     private String name;
     private String lastName;
     private List<Card> cards;
}
