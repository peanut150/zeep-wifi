package com.example.zeepwifi.dto;

public class AccountsCheckDTO {
    public String accountUsername;
    public String accountPassword;

    public AccountsCheckDTO(String accountUsername, String accountPassword) {
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }
}
