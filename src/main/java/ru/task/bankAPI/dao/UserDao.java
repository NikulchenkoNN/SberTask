package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.User;

import java.util.Set;

public interface UserDao {
    User findUserById(int userId);
    User findUserByName(String userName);
    User createUser(String userName);
    Set<User> getUsers();
}
