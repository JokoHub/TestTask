package com.vlad.dao.impl;

import com.vlad.exceptions.SQLFailException;
import com.vlad.dao.UserDAO;
import com.vlad.mapper.UserMapper;
import com.vlad.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
@Repository("userDAO")
public class UserJDBCTemplate implements UserDAO {

    private DataSource dataSource;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private JdbcTemplate jdbcTemplate;
    private User user;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createUser(String name, String login, String password) throws SQLFailException {
        try {
            jdbcTemplate.update(SQL_CREATE, name, login, passwordEncoder.encode(password));
        } catch (Exception e) {
            throw new SQLFailException("Couldn't create user with given parameters");
        }
    }

    public User getUser(String userName) throws SQLFailException {
        try {
            user = jdbcTemplate.queryForObject(SQL_GET, new Object[]{userName}, new UserMapper());
        } catch (Exception e) {
            throw new SQLFailException("Didn't find user with given userName");
        }
        return user;
    }

    public User getUserByLogin(String login) throws SQLFailException {
        try {
            user = jdbcTemplate.queryForObject(SQL_GETBYLOGIN, new Object[]{login}, new UserMapper());
        } catch (Exception e) {
            throw new SQLFailException(" Didn't find user with given login");
        }
        return user;
    }

    public List<User> listUsers() throws SQLFailException {
        List<User> users;
        try {
            users = jdbcTemplate.query(SQL_GETLIST, new UserMapper());
        } catch (Exception e){
            throw new SQLFailException("Couldn't get users from DB");
        }
        return users;
    }

    public void delete(Integer id) throws SQLFailException {
        try {
            jdbcTemplate.update(SQL_DELETE, id);
        } catch (Exception e) {
            throw new SQLFailException("Failed to delete user with given id");
        }
    }

    public void update(Integer id, String name, String login, String password) throws SQLFailException {
        try {
            jdbcTemplate.update(SQL_UPDATE, id, name, login, password);
        }
        catch (Exception e){
            throw new SQLFailException("Failed to update user with given parameters");
        }
    }


}
