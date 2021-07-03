package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.User;

import java.util.Set;

public interface UserDao {
    User findUserById(int userId);
    User findUserByName(String name);
    User createUser(String name);
    Set<User> getUsers();
    double getBalanceCard(User user, String cardNumber);
}
