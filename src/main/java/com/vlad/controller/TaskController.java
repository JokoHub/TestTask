package com.vlad.controller;

import com.vlad.exceptions.SQLFailException;
import com.vlad.model.SpringSecurityUser;
import com.vlad.service.TaskService.TaskService;
import com.vlad.service.TaskService.TaskServiceImpl;
import com.vlad.model.Task;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/7/2017.
 */
@Api(value = "tasks", description = "Task controller")
@RestController
public class TaskController {

    private Logger log = LoggerFactory.getLogger(Task.class.getName());

    @Autowired
    private TaskService taskService;
    private Task task;

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Returns a list of tasks",
            notes = "Returns a list of tasks assigned to user (it's id), who is authorized",
            response = Task.class,
            responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Failed to proceed request"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Successfully got tasks")})
    public ResponseEntity<List<Task>> getTasks() {
        List<Task> tasks;
        SpringSecurityUser user = (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            tasks = taskService.listTasks(user.getId());
        } catch (SQLFailException e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    @GetMapping("/tasks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get a task",
            notes = "Get a task by given TaskID",
            response = Task.class)
    @ApiResponses(value = {@ApiResponse(code = 404, message = "No tasks found for given ID"),
            @ApiResponse(code = 400, message = "Failed to proceed request"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Successfully got tasks")})
    public ResponseEntity<?> getTask(@ApiParam(name = "id", value = "ID of the task", required = true) @PathVariable("id") int id) {
        try {
            task = taskService.getTask(id);
        } catch (SQLFailException e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PostMapping(value = "/tasks")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Creates a task", notes = "Creates a task by given parameters")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid given parameters"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Successfully created")})
    public ResponseEntity<?> createTask(@ApiParam(name = "name", value = "task name", required = true) @RequestParam("name") String name,
                                        @ApiParam(name = "description", value = "brief description of the task", required = true) @RequestParam("description") String description) {
        try {
            taskService.createTask(name, description);
        } catch (SQLFailException e) {
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Task successfully created", HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Updates a task by given parameters")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Failed to process query"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Update was successfull")})
    public ResponseEntity<?> updateTask(@PathVariable("id") int id, @RequestBody Task task) {
        try{
        taskService.update(id, task.getTaskDescription());}
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Deletes a task by given id")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Failed to process query"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Delete was successfull")})
    public ResponseEntity<?> deleteTask(@ApiParam(name = "id", value = "ID of the task", required = true) @PathVariable("id") int id) {
        try{taskService.delete(id);}
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Task successfully deleted", HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Assigns a task to a user, whos ID is provided")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Failed to process query"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Assign was successfull")})
    public ResponseEntity<?> assignTask(@PathVariable("id") int id, @PathVariable("userId") int userId){
        try{taskService.assignTask(id, userId);}
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Task successfully assigned", HttpStatus.OK);
    }
}
