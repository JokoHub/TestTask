package com.vlad.model;

/**
 * Created by Fedyunkin_Vladislav on 2/9/2017.
 */
public class AuthenticationRequest {

    private String login;
    private String password;

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticationRequest{" +
                "username='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
