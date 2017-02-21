package com.vlad.service.UserService;

import com.vlad.exceptions.SQLFailException;
import com.vlad.dao.UserDAO;
import com.vlad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/6/2017.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public User getUserByName(String userName) throws SQLFailException {
        return userDAO.getUser(userName);
    }

    public void createUser(String name, String login, String password) throws SQLFailException {
        userDAO.createUser(name, login, password);
    }

    public List getUsers() throws SQLFailException {
        return userDAO.listUsers();
    }

    public User getUserByLogin(String login) throws SQLFailException {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public void delete(int id) throws SQLFailException {
        userDAO.delete(id);
    }

    @Override
    public void update(int id, String name, String login, String password) throws SQLFailException {
        userDAO.update(id, name, login, password);
    }
}
