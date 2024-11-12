package com.example.zeepwifi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AccountsUpdateDTO {
    public Long id;
    public String accountUsername;
    public String accountPassword;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
