package com.example.zeepwifi.dto;

public class AccountsDTO {
    public Long id;
    public String accountUsername;
    public String firstName;
    public String middleName;
    public String lastName;

    public AccountsDTO(Long id, String accountUsername, String firstName, String middleName, String lastName) {
        this.id = id;
        this.accountUsername = accountUsername;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
