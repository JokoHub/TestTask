package com.vlad.mapper;

import com.vlad.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
public class TaskMapper implements RowMapper<Task> {
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("id"));
        task.setTaskName(resultSet.getString("taskName"));
        task.setTaskDescription(resultSet.getString("taskDescription"));
        task.setUserId(resultSet.getInt("userId"));
        return task;
    }
}
