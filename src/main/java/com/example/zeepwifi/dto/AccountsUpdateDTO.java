package com.example.zeepwifi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AccountsUpdateDTO {
    public Long id;
    public String accountUsername;
    public String firstName;
    public String middleName;
    public String lastName;
    public String accountPassword;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
