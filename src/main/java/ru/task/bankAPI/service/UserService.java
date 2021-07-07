package ru.task.bankAPI.service;

import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.User;

public class UserService {
    static UserDao userDao = new UserDaoImpl();

    public static User createUser(String name) {
        User user = new User();
        user.setName(name);
        userDao.createUser(user);
        return user;
    }

    public static User findUserByName(String userName) {
        return userDao.findUserByName(userName);
    }

    public static User findUserById(Long userId) {
        return userDao.findUserById(userId);
    }
}
