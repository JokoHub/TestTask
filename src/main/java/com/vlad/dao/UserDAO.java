package com.vlad.dao;

import com.vlad.exceptions.SQLFailException;
import com.vlad.model.User;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
public interface UserDAO {

    static final String SQL_CREATE = "INSERT INTO users (userName, login, password) VALUES (?, ?, ?)";
    static final String SQL_GET = "SELECT * FROM users WHERE userName = ?";
    static final String SQL_GETBYLOGIN = "SELECT * FROM users WHERE login = ?";
    static final String SQL_GETLIST = "SELECT * FROM users";
    static final String SQL_DELETE = "DELETE FROM users WHERE id = ?";
    static final String SQL_UPDATE = "INSERT INTO users (id, userName, login, password) VALUES (?, ?, ?, ?)" +
            " ON DUPLICATE KEY UPDATE userName=VALUES(userName), login=VALUES(login), password=VALUES(password)";

    void createUser(String name, String login, String password) throws SQLFailException;

    User getUser(String userName) throws SQLFailException;

    User getUserByLogin(String login) throws SQLFailException;

    List<User> listUsers() throws SQLFailException;

    void delete(Integer id) throws SQLFailException;

    void update(Integer id, String name, String login, String password) throws SQLFailException;
}
