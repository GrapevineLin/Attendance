package edu.service.impl.impl;

import edu.bean.Employee;
import edu.bean.User;
import edu.dao.UserDao;
import edu.dao.impl.UserDaoImpl;
import edu.service.impl.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public Long insert(Employee bean) {
        return userDao.insert(bean);
    }

    public User LoadByName(String name) {
        return userDao.LoadByName(name);
    }

}
