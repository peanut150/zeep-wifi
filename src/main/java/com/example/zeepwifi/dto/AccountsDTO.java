package com.example.zeepwifi.dto;

public class AccountsDTO {
    public Long id;
    public String accountUsername;

    public AccountsDTO(Long id, String accountUsername) {
        this.id = id;
        this.accountUsername = accountUsername;
    }
}
