package com.example.zeepwifi.dto;

public class AccountsDto {
    public Long id;
    public String accountUsername;
    public String firstName;
    public String middleName;
    public String lastName;

    public AccountsDto(Long id, String accountUsername, String firstName, String middleName, String lastName) {
        this.id = id;
        this.accountUsername = accountUsername;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
