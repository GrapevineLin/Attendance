package edu.service.impl.impl;

import edu.bean.User;
import edu.dao.UserDao;
import edu.dao.impl.UserDaoImpl;
import edu.service.impl.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    public User LoadByName(String name) {
        return userDao.LoadByName(name);
    }

}
