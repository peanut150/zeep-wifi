package com.example.zeepwifi.dto;

import lombok.Data;

@Data
public class RegisterDto {
    public String accountUsername;
    public String firstName;
    public String middleName;
    public String lastName;
    public String accountPassword;
}
