package ru.task.bankAPI.services;

import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.User;

import java.util.Set;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userService;
    static UserDao userDao = new UserDaoImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (userService == null) {
            return new UserServiceImpl();
        } else {
            return userService;
        }
    }

    @Override
    public Set<User> getAllUsers() {
        return userDao.getUsers();
    }

    @Override
    public User createUser(String name) {
        User user = new User();
        user.setName(name);
        user = userDao.createUser(user);
        return user;
    }

    @Override
    public User findUserByName(String userName) {
        return userDao.findUserByName(userName);
    }

    @Override
    public User findUserById(Long userId) {
        return userDao.findUserById(userId);
    }
}
