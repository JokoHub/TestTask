package com.vlad.service.TaskService;

import com.vlad.exceptions.SQLFailException;
import com.vlad.dao.TaskDAO;
import com.vlad.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/6/2017.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO taskDAO;


    public void createTask(String name, String description) throws SQLFailException {
        taskDAO.createTask(name, description);
    }

    public Task getTask(Integer id) throws SQLFailException {
        return taskDAO.getTask(id);
    }

    public List<Task> listTasks(Integer id) throws SQLFailException {
        return taskDAO.listTasks(id);
    }

    public void delete(Integer id) throws SQLFailException {
        taskDAO.delete(id);
    }

    public void update(Integer id, String description) throws SQLFailException {
        taskDAO.update(id, description);
    }

    public void assignTask(Integer id, Integer userId) throws SQLFailException {
        taskDAO.assignTask(id, userId);
    }
}
