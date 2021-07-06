package ru.task.bankAPI.dao;

import ru.task.bankAPI.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    User findUserById(Long userId);
//    User findUserByName(String userName);
    User createUser(User user);
    List<User> getUsers();
}
