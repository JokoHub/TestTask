package com.vlad.dao;


import com.vlad.exceptions.SQLFailException;
import com.vlad.model.Task;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
public interface TaskDAO {

    static final String SQL_CREATE = "INSERT INTO tasks (taskName, taskDescription) VALUES (?, ?)";
    static final String SQL_GETBYID = "SELECT * FROM tasks WHERE id = ?";
    static final String SQL_GETLIST = "SELECT * FROM tasks WHERE userId = ?";
    static final String SQL_DELETE = "DELETE FROM tasks WHERE id = ?";
    static final String SQL_UPDATE = "UPDATE tasks SET taskDescription = ? WHERE id = ?";
    static final String SQL_ASSIGN = "UPDATE tasks SET userId = ? WHERE id = ?";

    void createTask(String name, String description) throws SQLFailException;

    Task getTask(Integer id) throws SQLFailException;

    List<Task> listTasks(Integer id) throws SQLFailException;

    void delete(Integer id) throws SQLFailException;

    void update(Integer id, String description) throws SQLFailException;

    void assignTask(Integer id, Integer userId) throws SQLFailException;
}
