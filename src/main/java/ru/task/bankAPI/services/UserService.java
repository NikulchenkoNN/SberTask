package ru.task.bankAPI.services;

import ru.task.bankAPI.model.User;

import java.util.Set;

public interface UserService {
    User createUser(String name);
    User findUserByName(String userName);
    User findUserById(Long userId);
    Set<User> getAllUsers();
}
