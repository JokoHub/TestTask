package com.vlad.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Fedyunkin_Vladislav on 2/2/2017.
 */
@ApiModel(value = "User", description = "User's model")
public class User {

    @ApiModelProperty(value = "user's id")
    private int id;
    @ApiModelProperty(value = "user's name")
    private String name;
    @ApiModelProperty(value = "user's login")
    private String login;
    @ApiModelProperty(value = "user's password")
    private String password;
    @ApiModelProperty(value = "user's authorities")
    private String authorities;

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public User() {
    }

    public User(int id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public User(String name, String login, String password) {
        this.name = name;
        this.password = password;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
