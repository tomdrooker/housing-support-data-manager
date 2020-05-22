package com.manager.data.housing.housingsupportmanager.model;

import javax.validation.constraints.NotBlank;

public class User {

    @NotBlank(message="Enter a valid username")
    private String username;

    @NotBlank(message="Enter a valid password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
