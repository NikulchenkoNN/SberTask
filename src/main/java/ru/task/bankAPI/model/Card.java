package ru.task.bankAPI.model;

import lombok.Data;

@Data
public class Card {
    private int id;
    private String number;
    private User user;
}
