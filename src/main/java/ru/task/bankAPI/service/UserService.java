package ru.task.bankAPI.service;

import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.User;

public class UserService {
    private UserDao userDao = new UserDaoImpl();

    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        userDao.createUser(user);
        return user;
    }
}
