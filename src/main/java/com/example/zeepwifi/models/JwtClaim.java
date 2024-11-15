package com.example.zeepwifi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class JwtClaim {
    public String accountUsername;

}