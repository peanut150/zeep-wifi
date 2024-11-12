package com.example.zeepwifi.models;

public class UserLogin {
    public String accountUsername;
    public String accountPassword;

    public UserLogin(String accountUsername, String accountPassword) {
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }
}
