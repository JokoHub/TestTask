package com.vlad.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
@ApiModel(value = "Task", description = "Task model")
public class Task {
    @ApiModelProperty(value = "Task's id")
    private int id;
    @ApiModelProperty(value = "Task's brief name")
    private String taskName;
    @ApiModelProperty(value = "Task's description")
    private String taskDescription;
    @ApiModelProperty(value = "Id of person, to whom this task is assigned")
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
