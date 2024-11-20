package com.example.zeepwifi.dto;

public class LoginDto {
    public String accountUsername;
    public String accountPassword;

    public LoginDto(String accountUsername, String accountPassword) {
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }
}
