package com.vlad.service.TaskService;

import com.vlad.exceptions.SQLFailException;
import com.vlad.model.Task;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/6/2017.
 */
public interface TaskService {
    void createTask (String name, String description) throws SQLFailException;
    Task getTask (Integer id) throws SQLFailException;
    List<Task> listTasks(Integer id) throws SQLFailException;
    void delete (Integer id) throws SQLFailException;
    void update (Integer id, String description) throws SQLFailException;
    void assignTask (Integer id, Integer userId) throws SQLFailException;
}
