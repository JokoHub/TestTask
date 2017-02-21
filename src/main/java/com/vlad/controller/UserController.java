package com.vlad.controller;

import com.vlad.exceptions.SQLFailException;
import com.vlad.service.UserService.UserService;
import com.vlad.service.UserService.UserServiceImpl;
import com.vlad.model.User;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Fedyunkin_Vladislav on 2/6/2017.
 */
@Api(value = "users", description = "User controller")
@RestController
public class UserController{
    private Logger log = LoggerFactory.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;
    private User user;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Return users from DB",
                notes = "Returns a complete list of users presented in DB",
                response = User.class,
                responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid given parameters"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<List<User>> getUsers(){
        List<User> users;
        try{
            users = userService.getUsers();
        } catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }



    @PostMapping (value = "/users")
    @ApiOperation(value = "Creates a new user and puts it into DB",
                notes = "Creates a new user and puts it into DB")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid given parameters"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Successfully created")})
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            userService.createUser(user.getName(), user.getLogin(), user.getPassword());
        }
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully created new user",HttpStatus.OK);
    }



    @GetMapping("/users/get/{userName}")
    @ApiOperation(value = "Get a user from DB", notes = "Gets user with requested login and pass", response = User.class)
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid given parameters"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<User> loginUser(@ApiParam(name = "userName", value = "Username of the user you are looking for", required = true)@PathVariable("userName") String userName){
        try {
            user = userService.getUserByName(userName);
        }
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<User>(user, HttpStatus.OK);
    }


    @GetMapping("/users/{login}")
    @ApiOperation(value = "Get a user from DB", notes = "Get a user with specified login")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Failed to process query"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Success")})
    public ResponseEntity<User> getUserByName(@ApiParam(name = "login", value = "Login of the user", required = true)@PathVariable("login") String login){
        try{user = userService.getUserByLogin( login);}
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation("Deletes a user by given id")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Failed to process query"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Delete was successful")})
    public ResponseEntity<?> deleteUser(@ApiParam(name = "id", value = "ID of the user", required = true) @PathVariable("id") int id) {
        try{userService.delete(id);}
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("User successfully deleted", HttpStatus.OK);
    }

    @PostMapping (value = "/users/update")
    @ApiOperation(value = "Updates a user with given parameters",
            notes = "Updates a user with given parameters")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid given parameters"),
            @ApiResponse(code = 401, message = "Restricted access"),
            @ApiResponse(code = 200, message = "Successfully updated")})
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try{
            userService.update(user.getId(), user.getName(), user.getLogin(), user.getPassword());
        }
        catch (SQLFailException e){
            log.error(e.getLocalizedMessage());
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully updated user",HttpStatus.OK);
    }

}
