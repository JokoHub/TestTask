package com.vlad.dao.impl;

import com.vlad.exceptions.SQLFailException;
import com.vlad.dao.TaskDAO;
import com.vlad.mapper.TaskMapper;
import com.vlad.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
@Repository("taskDAO")
public class TaskJDBCTemplate implements TaskDAO {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private Task task;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTask(String name, String description) throws SQLFailException {
        try {
            jdbcTemplate.update(SQL_CREATE, name, description);
        }
        catch (Exception e){
            throw new SQLFailException("Couldn't create task with given parameters");
        }
    }

    public Task getTask(Integer id) throws SQLFailException {
        try {
            task = jdbcTemplate.queryForObject(SQL_GETBYID, new Object[]{id}, new TaskMapper());
        }
        catch (Exception e){
            throw new SQLFailException("No task found with given ID");
        }
        return task;
    }

    public List<Task> listTasks(Integer id) throws SQLFailException {
        List<Task> taskList;
        try {
            taskList = jdbcTemplate.query(SQL_GETLIST, new Object[]{id}, new TaskMapper());
        }
        catch (Exception e){
            throw new SQLFailException("Couldn't load a taskList");
        }
        return taskList;
    }

    public void delete(Integer id) throws SQLFailException {
        try{
        task = jdbcTemplate.queryForObject(SQL_GETBYID, new Object[]{id}, new TaskMapper());
        } catch (Exception e){
            throw new SQLFailException("No task found with given ID");
        }
        jdbcTemplate.update(SQL_DELETE, id);
    }

    public void update(Integer id, String description) throws SQLFailException {
        try{
            task = jdbcTemplate.queryForObject(SQL_GETBYID, new Object[]{id}, new TaskMapper());
        }
        catch (Exception e){
            throw new SQLFailException("No task found with given ID");
        }
        jdbcTemplate.update(SQL_UPDATE, description, id);
    }

    public void assignTask(Integer id, Integer userId) throws SQLFailException {
        try{
        jdbcTemplate.update(SQL_ASSIGN, userId, id);}
        catch (Exception e){
            throw new SQLFailException("Invalid user or task ID");
        }
    }
}
