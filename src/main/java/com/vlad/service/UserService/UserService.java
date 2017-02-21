package com.vlad.service.UserService;

import com.vlad.exceptions.SQLFailException;
import com.vlad.model.User;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/6/2017.
 */
public interface UserService {

    User getUserByName(String userName) throws SQLFailException;

    List getUsers() throws SQLFailException;

    void createUser(String name, String login, String password) throws SQLFailException;

    User getUserByLogin(String login) throws SQLFailException;

    void delete(int id) throws SQLFailException;

    void update(int id, String name, String login, String password) throws SQLFailException;
}
